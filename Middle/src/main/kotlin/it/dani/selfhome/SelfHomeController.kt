package it.dani.selfhome

import it.dani.icontroller.Controller
import it.dani.icore.Connector
import it.dani.model.Device
import it.dani.model.DeviceState
import java.net.SocketTimeoutException

class SelfHomeController(private val connector: Connector): Controller() {

    override fun getDevices(): Set<Device> {
        try {
            this.connector.send("LST").let { response ->
                response.trim().split("\n").forEach { deviceName ->
                    this.devs += Device(deviceName, DeviceState("")).apply {
                        this.onGetState = { this@SelfHomeController.getState(this.name) }
                        this.onSetState = { deviceState ->  this@SelfHomeController.setState(this.name, deviceState) }
                    }.also { dev ->
                        this@SelfHomeController.onNewDevice.forEach { it(dev) }
                    }
                }
            }
        }catch (_: SocketTimeoutException) {}

        return this.devs
    }

    override fun getState(deviceName: String): DeviceState {
        return this.connector.send("GET;$deviceName").let { response ->
            DeviceState(response)
        }
    }

    override fun setState(deviceName: String, deviceState: DeviceState) {
        this.connector.send("SET;$deviceName;$deviceState").let { response ->
            if(response != "OK") { throw DeviceStateChangeException("Error: the response is $response") }
        }
    }
}