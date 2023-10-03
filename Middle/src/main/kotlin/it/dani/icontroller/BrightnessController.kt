package it.dani.icontroller

import it.dani.model.DeviceState

interface BrightnessController {
    fun getBrightness(deviceName: String): DeviceState
    fun setBrightness(deviceName: String, deviceState: DeviceState)
}