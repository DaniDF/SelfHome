package it.dani.selfhomeapp.middle.ipersistence

interface IDevicePersistence {
    fun freeText(text : String) : String
    fun toggleDevice(deviceName : String) : Boolean
    fun getDevices() : Set<String>
    fun setStateDevice(deviceName : String, state : Int) : Boolean
    fun getDeviceState(deviceName : String) : Int
}