package it.dani.controller

import it.dani.icontroller.Controller
import it.dani.model.Device
import it.dani.model.DeviceState

class TestController : Controller() {

    private val deviceState: MutableMap<String, DeviceState> = HashMap()

    init {
        val applyFn: Device.() -> Unit = {
            this.onGetState = { this@TestController.getState(this.name) }
            this.onSetState = { deviceState -> this@TestController.setState(this.name, deviceState) }
        }

        this.devs += setOf(
            Device("Light0", DeviceState("OFF")).apply(applyFn),
            Device("Light1", DeviceState("OFF")).apply(applyFn),
            Device("Light2", DeviceState("OFF")).apply(applyFn),
            Device("Light3", DeviceState("OFF")).apply(applyFn),

            Device("Button0", DeviceState("OFF")).apply(applyFn),
            Device("Button1", DeviceState("OFF")).apply(applyFn),
            Device("Button2", DeviceState("OFF")).apply(applyFn),
            Device("Button3", DeviceState("OFF")).apply(applyFn)
        )
    }
    override fun getDevices(): Set<Device> {
        return this.devs.onEach { device -> this.onNewDevice.onEach { it(device) } }
    }

    override fun getState(deviceName: String): DeviceState {
        return this.deviceState.getOrDefault(deviceName, DeviceState("OFF"))
    }

    override fun setState(deviceName: String, deviceState: DeviceState) {
        this.deviceState[deviceName] = deviceState
    }
}