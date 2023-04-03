package it.dani.selfhomeapp.middle.controller

import it.dani.selfhomeapp.middle.icontroller.IDeviceController
import it.dani.selfhomeapp.middle.icontroller.IGroupController
import it.dani.selfhomeapp.middle.ipersistence.IDevicePersistence
import it.dani.selfhomeapp.middle.ipersistence.IGroupPersistence
import it.dani.selfhomeapp.middle.persistence.Persistence
import java.util.concurrent.Executors

class Controller(address : String, port : Int) : IDeviceController, IGroupController {

    private val devicePersistence : IDevicePersistence = Persistence.getPersistence(address,port)
    private val groupPersistence : IGroupPersistence = Persistence.getPersistence(address,port)

    override fun freeText(text: String, consumer: (String) -> Unit, error: (Exception) -> Unit) {
        this.executeOnSeparatedThread {
            try {
                consumer(this.devicePersistence.freeText(text))
            } catch (e : Exception) {
                error(e)
            }
        }
    }

    override fun toggleDevice(deviceName: String, consumer: (Boolean) -> Unit, error: (Exception) -> Unit) {
        this.executeOnSeparatedThread {
            try {
                consumer(this.devicePersistence.toggleDevice(deviceName))
            } catch (e : Exception) {
                error(e)
            }
        }
    }

    override fun getDevices(consumer: (Set<String>) -> Unit) {
        this.getDevices(consumer) {}
    }

    override fun getDevices(consumer: (Set<String>) -> Unit, error: (Exception) -> Unit) {
        this.executeOnSeparatedThread {
            try {
                consumer(this.devicePersistence.getDevices())
            } catch (e : Exception) {
                error(e)
            }
        }
    }

    override fun getDeviceState(deviceName: String, consumer: (Int) -> Unit) {
        this.getDeviceState(deviceName){}
    }

    override fun getDeviceState(deviceName: String, consumer: (Int) -> Unit, error: (Exception) -> Unit) {
        this.executeOnSeparatedThread {
            try {
                consumer(this.devicePersistence.getDeviceState(deviceName))
            } catch (e : Exception) {
                error(e)
            }
        }
    }

    override fun setDeviceState(deviceName: String, state: Int, consumer: (Boolean) -> Unit) {
        this.setDeviceState(deviceName,state) {}
    }

    override fun setDeviceState(deviceName: String, state: Int, consumer: (Boolean) -> Unit, error: (Exception) -> Unit) {
        this.executeOnSeparatedThread {
            try {
                consumer(this.devicePersistence.setStateDevice(deviceName, state))
            } catch (e : Exception) {
                error(e)
            }
        }
    }

    override fun getGroups(consumer: (Set<String>) -> Unit) {
        this.getGroups(consumer) {}
    }

    override fun getGroups(consumer: (Set<String>) -> Unit, error: (Exception) -> Unit) {
        this.executeOnSeparatedThread {
            try {
                consumer(this.groupPersistence.getGroups())
            } catch (e : Exception) {
                error(e)
            }
        }
    }

    override fun setGroupState(groupName: String, state: Int, consumer: (Boolean) -> Unit) {
        this.setGroupState(groupName,state,consumer) {}
    }

    override fun setGroupState(groupName: String, state: Int, consumer: (Boolean) -> Unit, error: (Exception) -> Unit) {
        this.executeOnSeparatedThread {
            try {
                consumer(this.groupPersistence.setStateGroup(groupName,state))
            } catch (e : Exception) {
                error(e)
            }
        }
    }

    override fun addNewGroup(groupName: String, consumer: (Boolean) -> Unit) {
        this.addNewGroup(groupName, consumer) {}
    }

    override fun addNewGroup(groupName: String, consumer: (Boolean) -> Unit, error: (Exception) -> Unit) {
        this.executeOnSeparatedThread {
            try {
                consumer(this.groupPersistence.addNewGroup(groupName))
            } catch (e : Exception) {
                error(e)
            }
        }
    }

    override fun delGroup(groupName: String, consumer: (Boolean) -> Unit) {
        this.delGroup(groupName, consumer) {}
    }

    override fun delGroup(groupName: String, consumer: (Boolean) -> Unit, error: (Exception) -> Unit) {
        this.executeOnSeparatedThread {
            try {
                consumer(this.groupPersistence.delGroup(groupName))
            } catch (e : Exception) {
                error(e)
            }
        }
    }

    override fun getDevicesFromGroup(groupName: String, consumer: (Set<String>) -> Unit) {
        this.getDevicesFromGroup(groupName, consumer) {}
    }

    override fun getDevicesFromGroup(groupName: String, consumer: (Set<String>) -> Unit, error: (Exception) -> Unit) {
        this.executeOnSeparatedThread {
            try {
                consumer(this.groupPersistence.getDeviceFromGroup(groupName))
            } catch (e : Exception) {
                error(e)
            }
        }
    }

    override fun addDeviceToGroup(deviceName: String, groupName: String, consumer: (Boolean) -> Unit) {
        this.addDeviceToGroup(deviceName, groupName, consumer) {}
    }

    override fun addDeviceToGroup(deviceName: String, groupName: String, consumer: (Boolean) -> Unit, error: (Exception) -> Unit) {
        this.executeOnSeparatedThread {
            try {
                consumer(this.groupPersistence.addDeviceToGroup(deviceName, groupName))
            } catch (e : Exception) {
                error(e)
            }
        }
    }

    override fun delDeviceToGroup(deviceName: String, groupName: String, consumer: (Boolean) -> Unit) {
        this.delDeviceToGroup(deviceName, groupName, consumer) {}
    }

    override fun delDeviceToGroup(deviceName: String, groupName: String, consumer: (Boolean) -> Unit, error: (Exception) -> Unit) {
        this.executeOnSeparatedThread {
            try {
                consumer(this.groupPersistence.delDeviceToGroup(deviceName, groupName))
            } catch (e : Exception) {
                error(e)
            }
        }
    }

    private fun executeOnSeparatedThread(cmd : () -> Any) {
        Executors.newSingleThreadExecutor().also {
            it.submit(cmd)
        }
    }
}