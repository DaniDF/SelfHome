package it.dani.selfhomeapp.middle.icontroller

interface IDeviceController {
    fun freeText(text: String, consumer : (String) -> Unit, error : (Exception) -> Unit)
    fun toggleDevice(deviceName : String, consumer : (Boolean) -> Unit, error : (Exception) -> Unit)
    fun getDevices(consumer : (Set<String>) -> Unit)
    fun getDeviceState(deviceName : String, consumer : (Int) -> Unit)
    fun setDeviceState(deviceName : String, state : Int, consumer : (Boolean) -> Unit)
    fun getDevices(consumer : (Set<String>) -> Unit, error : (Exception) -> Unit)
    fun getDeviceState(deviceName : String, consumer : (Int) -> Unit, error : (Exception) -> Unit)
    fun setDeviceState(deviceName : String, state : Int, consumer : (Boolean) -> Unit, error : (Exception) -> Unit)
}