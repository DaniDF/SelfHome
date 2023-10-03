package it.dani.controller

import it.dani.icontroller.BrightnessController
import it.dani.icontroller.Controller
import it.dani.model.Device
import it.dani.model.DeviceState
import java.util.Random

class TestController : Controller(), BrightnessController {

    private val random = Random(0)

    private val deviceState: MutableMap<String, DeviceState> = HashMap()
    private val deviceBrightness: MutableMap<String, DeviceState> = HashMap()

    init {
        val applyFn: Device.() -> Unit = {
            this.onGetState = { this@TestController.getState(this.name) }
            this.onSetState = { deviceState -> this@TestController.setState(this.name, deviceState) }

            this.onGetBrightness = { this@TestController.getBrightness(this.name) }
            this.onSetBrightness = { deviceBrightness -> this@TestController.setBrightness(this.name, deviceBrightness)}
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
        return if(deviceName.lowercase().startsWith("button")){
            val buttonState = when(this.random.nextBoolean()) {
                true -> DeviceState("ON")
                false -> DeviceState("OFF")
            }

            buttonState

        } else {
            this.deviceState.getOrDefault(deviceName, DeviceState("OFF"))
        }
    }

    override fun getBrightness(deviceName: String): DeviceState {
        return this.deviceBrightness.getOrDefault(deviceName, DeviceState("0"))
    }

    override fun setState(deviceName: String, deviceState: DeviceState) {
        this.deviceState[deviceName] = deviceState
    }

    override fun setBrightness(deviceName: String, deviceState: DeviceState) {
        this.deviceBrightness[deviceName] = deviceState
    }
}