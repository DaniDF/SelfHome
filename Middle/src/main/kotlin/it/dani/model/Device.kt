package it.dani.model

import it.dani.selfhome.DeviceStateChangeException

data class Device(
    val name: String,
    val initDeviceState: DeviceState
) {
    var onSetUpdateState: (DeviceState) -> Any = { }
    val onStateChange: MutableList<(Device) -> Any> = ArrayList()
    var onGetUpdateState: (Device) -> DeviceState = { DeviceState("") }

    val settings: MutableMap<String, String> = HashMap()

    var state: DeviceState = this.initDeviceState
        set(value) {
            if(field != value) {
                try {
                    this.onSetUpdateState(value)
                    field = value
                    this.onStateChange.forEach { it(this) }
                } catch (e: DeviceStateChangeException) {
                    throw DeviceStateChangeException(e.message)
                }
            }
        }
        get() {
            val value = this.onGetUpdateState(this)

            if(field != value) {
                field = value
                this.onStateChange.forEach { it(this) }
            }

            return field
        }

    override fun toString(): String {
        return "$name [$state] $settings"
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