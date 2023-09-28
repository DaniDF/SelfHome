package it.dani.selfhome

import it.dani.icore.Connector
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class SelfHomeConnector(private val serverAddress: InetAddress, private val port: Int) : Connector {
    private val sock = DatagramSocket()

    @Synchronized
    override fun send(data: String, timeout: Int): String {
        var buffer = (data).toByteArray() + 0
        DatagramPacket(buffer, buffer.size, this.serverAddress, this.port).also { pack ->
            this.sock.send(pack)
        }

        buffer = ByteArray(1024)
        return DatagramPacket(buffer, buffer.size, this.serverAddress, port).let { pack ->
            this.sock.soTimeout = timeout

            this.sock.receive(pack)
            String(pack.data, 0, pack.length-1)
        }
    }
}