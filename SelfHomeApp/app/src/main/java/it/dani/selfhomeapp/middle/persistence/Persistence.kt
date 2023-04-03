package it.dani.selfhomeapp.middle.persistence

import android.util.Log
import it.dani.selfhomeapp.middle.ipersistence.IDevicePersistence
import it.dani.selfhomeapp.middle.ipersistence.IGroupPersistence
import java.io.*
import java.lang.StringBuilder
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.*
import kotlin.IllegalArgumentException

class Persistence private constructor(private val address: InetAddress, private val port: Int) : IDevicePersistence, IGroupPersistence {

    init {
        if(port <= 0 || port > 65535) throw IllegalArgumentException("Port must be positive")
    }

    override fun freeText(text: String): String {
        return try {
            this.communicate(text)

        } catch (e : IOException) {
            Log.e("freeText","Error: ${e.message}")
            ""
        }
    }

    override fun toggleDevice(deviceName: String): Boolean {
        return try {
            val response = this.communicate("TGL DISP <${deviceName}>;")
            if(response.isNotEmpty()) {
                response.toBoolean()
            } else {
                false
            }
        } catch (e : IOException) {
            Log.e("toggleDevice","Error: ${e.message}")
            false
        }
    }

    override fun getDevices(): MutableSet<String> {
        val result = TreeSet<String>()

        try {
            val response = this.communicate("LST;")
            if(response.isNotEmpty()) {
                result += response.split(";")
            }

        } catch (e : IOException) {
            Log.e("getDevice","Error: ${e.message}")
        }

        return result
    }

    override fun setStateDevice(deviceName: String, state: Int): Boolean {
        return try {
            this.communicate("SET DISP <${deviceName}> ${state};").lowercase(Locale.getDefault()).toBooleanStrict()
        } catch (e : IOException) {
            Log.e("setDeviceState","Error: ${e.message}")
            false
        }
    }

    override fun getDeviceState(deviceName: String): Int {
        try {
            val result = this.communicate("GET DISP <${deviceName}>;")
            return if (result == "OFF") {
                0
            } else {
                result.toInt()
            }
        } catch(e : IOException) {
            Log.e("getDeviceState","Error: ${e.message}")
            throw SelfHomePersistenceException()   //TODO inserisci e
        }
    }

    override fun getGroups(): MutableSet<String> {
        val result = TreeSet<String>()

        try {
            val response = this.communicate("LGR;")
            if(response.isNotEmpty()) {
                result += response.split(";")
            }

        } catch (e : IOException) {
            Log.e("getGroups","Error: ${e.message}")
        }

        return result
    }

    override fun setStateGroup(groupName: String, state: Int): Boolean {
        return try {
            this.communicate("SET GRUP <${groupName}> <${state}>;").lowercase(Locale.getDefault()).toBooleanStrict()
        } catch (e : IOException) {
            Log.e("setStateGroup","Error: ${e.message}")
            false
        }
    }

    override fun addNewGroup(groupName: String): Boolean {
        return try {
            this.communicate("NGR <${groupName}>;").lowercase(Locale.getDefault()).toBooleanStrict()
        } catch (e : IOException) {
            Log.e("addNewGroup","Error: ${e.message}")
            false
        }
    }

    override fun delGroup(groupName: String): Boolean {
        return try {
            this.communicate("DGR <${groupName}>;").lowercase(Locale.getDefault()).toBooleanStrict()
        } catch (e : IOException) {
            Log.e("delGroup","Error: ${e.message}")
            false
        }
    }

    override fun getDeviceFromGroup(groupName: String): MutableSet<String> {
        val result = TreeSet<String>()

        try {
            val response = this.communicate("LDG <${groupName}>;")
            if(response.isNotEmpty()) {
                result += response.split(";")
            }

        } catch (e : IOException) {
            Log.e("getDeviceFromGroup","Error: ${e.message}")
        }

        return result
    }

    override fun addDeviceToGroup(deviceName: String, groupName: String): Boolean {
        return try {
            this.communicate("ADG <${deviceName}> <${groupName}>;").lowercase(Locale.getDefault()).toBooleanStrict()
        } catch (e : IOException) {
            Log.e("addDeviceToGroup","Error: ${e.message}")
            false
        }
    }

    override fun delDeviceToGroup(deviceName: String, groupName: String): Boolean {
        return try {
            this.communicate("DDG <${deviceName}> <${groupName}>;").lowercase(Locale.getDefault()).toBooleanStrict()
        } catch (e : IOException) {
            Log.e("delDeviceToGroup","Error: ${e.message}")
            false
        }
    }

    private fun communicate(str : String) : String {
        return this.communicate(str,Timeout.STANDARD)
    }

    private fun communicate(str : String, timeout : Timeout) : String {
        val sock = DatagramSocket()

        val buffer = ByteArray(1024)

        val pack = DatagramPacket(buffer,buffer.size,this.address,this.port)
        val baos = ByteArrayOutputStream()
        val osw = OutputStreamWriter(baos)

        osw.write(str)
        osw.flush()
        pack.data = baos.toByteArray()

        sock.send(pack)
        sock.soTimeout = timeout.value

        pack.data = buffer
        sock.receive(pack)
        sock.close()

        val dis = DataInputStream(ByteArrayInputStream(pack.data))
        val result = StringBuilder()

        var car = dis.read()
        while (car > 0) {
            result.append(Char(car))
            car = dis.read()
        }

        return result.toString()
    }

    companion object {
        fun getPersistence(addr : String,port : Int) : Persistence {
            if(port <= 0 || port > 65535) throw IllegalArgumentException("Error: invalid port value")
            val address = InetAddress.getByName(addr)
            return Persistence(address,port)
        }
    }

    enum class Timeout(val value : Int) {
        INFINITY(0),
        STANDARD(5000)
    }
}