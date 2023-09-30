package it.dani.listener

import it.dani.icontroller.Controller
import it.dani.listener.parser.DFLiteBaseVisitor
import it.dani.listener.parser.DFLiteLexer
import it.dani.listener.parser.DFLiteParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket

class RequestListener(private val listenPort: Int, private val controller: Controller): Listener {
    private val thread = Thread {
        with(DatagramSocket(this.listenPort)) {
            this.reuseAddress = true

            while(true) {
                val buffer = ByteArray(2048)
                val pack = DatagramPacket(buffer, buffer.size)
                try {
                    this.receive(pack)

                    Thread {
                        String(pack.data, 0, pack.length-1).let { request ->
                            println(request)
                            val response = this@RequestListener.handleRequest(request)
                            pack.data = response.toByteArray() + 0
                            this.send(pack)
                        }
                    }.start()

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }.apply {
        this.priority = Thread.MAX_PRIORITY
    }

    override fun start() {
        this.thread.start()
    }

    override fun stop() {
        this.thread.interrupt()
    }

    private fun handleRequest(request: String): String {
        val lexer = DFLiteLexer(CharStreams.fromString(request))
        val parser = DFLiteParser(CommonTokenStream(lexer))

        val tree = parser.program()
        val visitor = DFLiteBaseVisitor(this.controller)

        val parseResult = visitor.visit(tree)
        val result = if(parseResult != null && parseResult.isPresent) {
            parseResult.get()
        } else {
            "ERR"
        }

        return result.toString()
    }
}