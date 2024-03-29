package it.dani.model

import it.dani.selfhome.DeviceStateChangeException

data class Device(
    val name: String,
    val initDeviceState: DeviceState
) {
    var onSetState: (DeviceState) -> Any = { }
    val onStateChange: MutableList<(Device) -> Any> = ArrayList()
    var onGetState: (Device) -> DeviceState = { this.initDeviceState }

    var onSetBrightness: (DeviceState) -> Any = { }
    val onBrightnessChange: MutableList<(Device) -> Any> = ArrayList()
    var onGetBrightness: (Device) -> DeviceState = { this.initDeviceState }

    val settings: MutableMap<String, String> = HashMap()

    var state: DeviceState = this.initDeviceState
        set(value) {
            try {
                this.onSetState(value)
                field = value
                this.onStateChange.forEach { it(this) }
            } catch (e: DeviceStateChangeException) {
                throw e
            }
        }
        get() {
            val value = this.onGetState(this)

            if(field != value) {
                field = value
                this.onStateChange.forEach { it(this) }
            }

            return field
        }

    var brightness: DeviceState = this.initDeviceState
        set(value) {
            if(field != value) {
                try {
                    this.onSetBrightness(value)
                    field = value
                    this.onBrightnessChange.forEach { it(this) }
                } catch (e: DeviceStateChangeException) {
                    throw e
                }
            }
        }
        get() {
            val value = this.onGetBrightness(this)

            if(field != value) {
                field = value
                this.onBrightnessChange.forEach { it(this) }
            }

            return field
        }

    override fun toString(): String {
        return "$name [$state] [$brightness] $settings"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Device) return false

        if (name != other.name) return false
        if (settings != other.settings) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + settings.hashCode()
        return result
    }
}