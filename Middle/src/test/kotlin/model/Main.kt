package model

import java.io.InputStream
import java.io.OutputStream

fun main(args : Array<String>) {
    val stateOff = ValueSmartState(0,"OFF")
    val stateOn = ValueSmartState(1,"ON")

    val smartLight = StatefulSmartDevice(0x01D,"Luce calda 1",false,LightConnection(),stateOff).apply {
        actions += DiscreteSmartAction(0x01A,this,"Turn",TempDriverTurnLight(),listOf(stateOff,stateOn))
    }

    smartLight.actions.first().execute(stateOn)
}

class TempDriverTurnLight : SmartDriver {
    override fun execute(device : SmartDevice, args: List<Any>): Any {
        return when(args.size) {
            1 -> device.connection.getOutput().write(args[0].toString().toByteArray())
            else -> throw DriverParameterException("${args.size} parameters found, only 1 is allowed")
        }
    }
}

class LightConnection : SmartDeviceConnection {
    override fun getInput(): InputStream {
        TODO("Not yet implemented")
    }

    override fun getOutput(): OutputStream {
        TODO("Not yet implemented")
    }

}