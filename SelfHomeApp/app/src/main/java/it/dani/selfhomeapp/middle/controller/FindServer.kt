package it.dani.selfhomeapp.middle.controller

import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.io.IOException
import java.io.Serializable
import java.lang.NumberFormatException
import java.lang.StringBuilder
import java.net.DatagramPacket
import java.net.InetAddress
import java.net.MulticastSocket
import java.util.concurrent.Executors

class FindServer : Serializable {

    fun search(consumer: (Data) -> Unit, onError : (Exception) -> Any) {
        Executors.newSingleThreadExecutor().also { exe ->
            exe.submit {
                try {
                    MulticastSocket().use {
                        val group = InetAddress.getByName(MULTICAST_ADDRESS)
                        it.joinGroup(group)
                        it.reuseAddress = true
                        it.soTimeout = 1000

                        val buffer = ByteArray(128)
                        val pack = DatagramPacket(buffer, buffer.size, group, MULTICAST_PORT)
                        it.send(pack)

                        buffer.fill(0.toByte())
                        pack.data = buffer
                        it.receive(pack)

                        val dis = DataInputStream(ByteArrayInputStream(pack.data))
                        val response = handleResponse(dis)
                        dis.close()

                        val responseP = response.split(":")
                        if (responseP.size != 2) {
                            throw IOException("Bad response: $response")
                        }

                        val data: Data
                        try {
                            val port = responseP[1].toInt()
                            if (port <= 0 || port > 65535) {
                                throw IOException("Bad response: $response")
                            }

                            data = Data(responseP[0], port)
                        } catch (e: NumberFormatException) {
                            throw IOException("Bad response: $response", e)
                        }

                        consumer(data)
                    }

                } catch (e: IOException) {
                    e.printStackTrace()
                    onError(e)
                }
            }
        }
    }

    private fun handleResponse(dis: DataInputStream): String {
        val result = StringBuilder()

        var car = dis.read()
        while (car > 0) {
            result.append(Char(car))
            car = dis.read()
        }

        return result.toString()
    }

    companion object {
        private const val MULTICAST_ADDRESS = "229.255.255.250"
        private const val MULTICAST_PORT = 4444
    }

    data class Data(val address: String, val port: Int) : Serializable {
        override fun toString(): String {
            return "${this.address}:${this.port}"
        }
    }
}