package it.dani.homeassistant

import com.google.gson.GsonBuilder
import it.dani.icontroller.Controller
import it.dani.model.Device
import java.io.File

object HomeAssistantService {

    private const val TOPIC = "SelfHome"
    private const val UUID = "$TOPIC-d8e1-40ef-9e59-0d66b3bc525e"

    fun startService(ipBroker: String, portBroker: Int, settingsPath: File, controller: Controller, topic: String = TOPIC, uuid: String = UUID): HomeAssistant {
        val uriBroker = "tcp://$ipBroker:$portBroker"
        val mqttClient = MqttClientResilient(uriBroker, uuid)

        val ha = HomeAssistant(mqttClient, controller).apply {
            this.connect()
            this.subscribe(topic)
        }

        controller.onNewDevice += { dev ->
            searchSettings(dev, settingsPath)
            ha.declareDevice(dev)
        }

        return ha
    }

    private fun searchSettings(device: Device, settingsPath: File) {
        val gson = GsonBuilder().create()

        when(settingsPath.isDirectory) {
            true -> {
                settingsPath.list()?.forEach { file ->
                    this.searchSettings(device, File(settingsPath, file))
                }
            }
            false -> {
                if (settingsPath.name.endsWith(".json") && device.name == settingsPath.name.removeSuffix(".json")) {
                    println("found: ${device.name}")
                    val config = (gson.fromJson(settingsPath.readText(), Map::class.java) as MutableMap<*, *>).map { (key, value) ->
                        if(key in arrayOf("qos", "expire_after")) {
                            key.toString() to value.toString().toFloat().toInt().toString()
                        } else {
                            key.toString() to value.toString()
                        }

                    }
                    device.settings += config
                }
            }
        }
    }
}