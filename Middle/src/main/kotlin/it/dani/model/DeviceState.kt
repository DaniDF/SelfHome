package it.dani.model

data class DeviceState(val state: Any) {

    override fun toString(): String {
        return this.state.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DeviceState) return false

        if (state != other.state) return false

        return true
    }

    override fun hashCode(): Int {
        return state.hashCode()
    }
}