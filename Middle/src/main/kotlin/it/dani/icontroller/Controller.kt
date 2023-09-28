package it.dani.icontroller

import it.dani.model.Device
import it.dani.model.DeviceState

abstract class Controller {
    val devs :MutableSet<Device> = HashSet()
    val onNewDevice: MutableSet<(Device) -> Any> = HashSet()

    abstract fun getDevices(): Set<Device>
    abstract fun getState(deviceName: String): DeviceState
    abstract fun setState(deviceName: String, deviceState: DeviceState)
}