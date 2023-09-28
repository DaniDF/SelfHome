package it.dani.homeassistant

import com.google.gson.GsonBuilder
import it.dani.icontroller.Controller
import it.dani.model.Device
import it.dani.model.DeviceState
import it.dani.selfhome.DeviceStateChangeException
import org.eclipse.paho.mqttv5.client.IMqttClient
import org.eclipse.paho.mqttv5.common.MqttMessage

class HomeAssistant(private val mqttClient: IMqttClient, private val controller: Controller) {

    private val mqttCallback = MqttCallbackImp().apply {
        listeners += { p0, p1 ->
            this@HomeAssistant.controller.devs
                .find { dev -> dev.settings.contains("command_topic") && dev.settings["command_topic"] == p0 }
                ?.let { dev ->
                    try {
                        dev.state = DeviceState(p1)
                    } catch (_: DeviceStateChangeException) {
                        println("Error while changing state $dev")
                    }
                }
        }
    }

    fun connect() {
        this.mqttClient.setCallback(this.mqttCallback)
        this.mqttClient.connect()
    }

    fun subscribe() {
        this.mqttClient.subscribe("homeassistant/#", 0)
        this.mqttClient.subscribe("SelfHome/#", 0)
    }

    fun disconnect() {
        this.mqttClient.disconnect()
    }

    internal fun declareDevice(device: Device) {
        val deviceType = device.settings["config"]!!
        device.settings.remove("config")
        val gson = GsonBuilder().create()
        val json = gson.toJson(device.settings)
        this.mqttClient.publish("homeassistant/$deviceType/${device.name.replace(" ", "_")}/config", json.toByteArray(), 1, true)

        device.onStateChange += { this.updateDeviceStatus(it) }
    }

    private fun updateDeviceStatus(device: Device) {
        val messageString = device.state.toString()
        val mqttMessage = MqttMessage(messageString.toByteArray())
        this.mqttClient.publish(device.settings["state_topic"], mqttMessage)

    }
}