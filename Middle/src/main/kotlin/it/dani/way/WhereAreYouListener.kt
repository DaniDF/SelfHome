package it.dani.way

import it.dani.listener.Listener
import java.io.IOException
import java.net.DatagramPacket
import java.net.InetAddress
import java.net.InterfaceAddress
import java.net.MulticastSocket
import java.net.NetworkInterface

class WhereAreYouListener(address: InetAddress, listenPort: Int, servicePort: Int): Listener {
    private val thread = Thread {
        with(MulticastSocket(listenPort)) {
            this.reuseAddress = true
            this.soTimeout = 0
            this.joinGroup(address)

            while(true) {
                val buffer = ByteArray(512)
                val pack = DatagramPacket(buffer, buffer.size)
                try {
                    this.receive(pack)

                    Thread {
                        println("Received: WAY from ${pack.address.hostAddress}")
                        val response = this@WhereAreYouListener.handleRequest(pack, servicePort)
                        println("response: $response")
                        pack.data = response.toByteArray() + 0
                        this.send(pack)
                    }.start()

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun start() {
        this.thread.start()
    }

    override fun stop() {
        this.thread.interrupt()
    }

    private fun handleRequest(packet: DatagramPacket, servicePort: Int): String {
        var result: String? = null

        NetworkInterface.getNetworkInterfaces().iterator().forEach { networkInterface ->
            networkInterface.interfaceAddresses.forEach { interfaceAddress ->
                if(result == null && this.isInSameNet(packet.address, interfaceAddress)) {
                    result = interfaceAddress.address.hostAddress
                }
            }
        }
        return "${result ?: InetAddress.getLocalHost().hostAddress}:$servicePort"
    }

    private fun isInSameNet(address: InetAddress, interfaceAddress: InterfaceAddress): Boolean {
        var result = address.address.size == interfaceAddress.address.address.size

        if(result) {
            for(count in 0..<(interfaceAddress.networkPrefixLength/8)) {
                if(address.address[count] != interfaceAddress.address.address[count]) {
                    result = false
                }
            }
        }

        return result
    }
}