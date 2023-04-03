package it.dani.selfhomeapp.middle.ipersistence

interface IGroupPersistence {
    fun getGroups() : Set<String>
    fun setStateGroup(groupName : String, state : Int) : Boolean
    fun addNewGroup(groupName : String) : Boolean
    fun delGroup(groupName : String) : Boolean
    fun getDeviceFromGroup(groupName : String) : Set<String>
    fun addDeviceToGroup(deviceName : String,groupName : String) : Boolean
    fun delDeviceToGroup(deviceName : String, groupName: String) : Boolean
}