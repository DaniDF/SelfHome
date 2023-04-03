package it.dani.selfhomeapp.middle.icontroller

interface IGroupController {
    fun getGroups(consumer: (Set<String>) -> Unit)
    fun setGroupState(groupName: String, state: Int, consumer: (Boolean) -> Unit)
    fun addNewGroup(groupName: String, consumer: (Boolean) -> Unit)
    fun delGroup(groupName: String, consumer: (Boolean) -> Unit)
    fun getDevicesFromGroup(groupName: String, consumer: (Set<String>) -> Unit)
    fun addDeviceToGroup(deviceName: String, groupName: String, consumer: (Boolean) -> Unit)
    fun delDeviceToGroup(deviceName: String, groupName: String, consumer: (Boolean) -> Unit)

    fun getGroups(consumer: (Set<String>) -> Unit, error: (Exception) -> Unit)
    fun setGroupState(groupName: String, state: Int, consumer: (Boolean) -> Unit, error: (Exception) -> Unit)

    fun addNewGroup(groupName: String, consumer: (Boolean) -> Unit, error: (Exception) -> Unit)
    fun delGroup(groupName: String, consumer: (Boolean) -> Unit, error: (Exception) -> Unit)
    fun getDevicesFromGroup(groupName: String, consumer: (Set<String>) -> Unit, error: (Exception) -> Unit)
    fun addDeviceToGroup(deviceName: String, groupName: String, consumer: (Boolean) -> Unit, error: (Exception) -> Unit)
    fun delDeviceToGroup(deviceName: String, groupName: String, consumer: (Boolean) -> Unit, error: (Exception) -> Unit)
}