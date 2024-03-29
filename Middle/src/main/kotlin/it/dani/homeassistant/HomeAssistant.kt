package it.dani.homeassistant

import com.google.gson.GsonBuilder
import it.dani.icontroller.Controller
import it.dani.model.Device
import it.dani.model.DeviceState
import it.dani.selfhome.DeviceStateChangeException
import org.eclipse.paho.mqttv5.client.IMqttClient
import org.eclipse.paho.mqttv5.common.MqttMessage
import kotlin.math.max

class HomeAssistant(private val mqttClient: IMqttClient, private val controller: Controller) {

    private val mqttCallback = MqttCallbackImp(debug = true).apply {
        this.onMessage += { p0, p1 ->
            this@HomeAssistant.controller.devs
                .find { dev -> dev.settings.contains("command_topic") && dev.settings["command_topic"] == p0 }
                ?.let { dev ->
                    try {
                        dev.state = DeviceState(p1)
                    } catch (e: DeviceStateChangeException) {
                        println("Error while changing state $dev\n${e.message}")
                    }
                }

            this@HomeAssistant.controller.devs
                .find { dev -> dev.settings.contains("brightness_command_topic") && dev.settings["brightness_command_topic"] == p0 }
                ?.let { dev ->
                    try {
                        dev.brightness = DeviceState(p1)
                    } catch (e: DeviceStateChangeException) {
                        println("Error while changing brightness $dev\n${e.message}")
                    }
                }
        }

        this.onError += { p0 ->
            println("Error occurred: ${p0.message}\n${p0.printStackTrace()}")
        }
    }

    fun connect() {
        this.mqttClient.setCallback(this.mqttCallback)
        this.mqttClient.connect()
    }

    fun subscribe(selfhomeTopic: String) {
        this.mqttClient.subscribe("homeassistant/#", 0)
        this.mqttClient.subscribe("$selfhomeTopic/#", 0)
    }

    fun disconnect() {
        this.mqttClient.disconnect()
    }

    internal fun declareDevice(device: Device) {
        if(device.settings["config"] == null) {
            println("${device.name} does not have any configuration")

        } else {
            val deviceType = device.settings["config"]!!
            device.settings.remove("config")
            val gson = GsonBuilder().create()
            val json = gson.toJson(device.settings)

            if(device.settings["expire_after"] != null) {

                val expireTimeout: Long = max(1L, device.settings["expire_after"]!!.toFloat().toLong() / 30)

                Thread {
                    while(true) {
                        device.state
                        this.updateDeviceStatus(device)
                        Thread.sleep(expireTimeout * 1000)
                    }
                }.apply {
                    this.priority = Thread.MIN_PRIORITY
                }.start()

            } else {
                device.onStateChange += { this.updateDeviceStatus(it) }
            }

            if(device.settings["brightness_state_topic"] != null) {
                device.onBrightnessChange += { this.updateDeviceBrightness(it) }
            }

            this.mqttClient.publish("homeassistant/$deviceType/${device.name.replace(" ", "_")}/config", json.toByteArray(), 1, true)
        }
    }

    private fun updateDeviceStatus(device: Device) {
        val messageString = device.state.toString()
        val mqttMessage = MqttMessage(messageString.toByteArray())
        this.mqttClient.publish(device.settings["state_topic"], mqttMessage)

    }

    private fun updateDeviceBrightness(device: Device) {
        val messageString = device.brightness.toString()
        val mqttMessage = MqttMessage(messageString.toByteArray())
        this.mqttClient.publish(device.settings["brightness_state_topic"], mqttMessage)
    }
}