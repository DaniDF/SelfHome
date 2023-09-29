package it.dani.homeassistant

import com.google.gson.GsonBuilder
import it.dani.icontroller.Controller
import it.dani.model.Device
import org.eclipse.paho.mqttv5.client.MqttClient
import java.io.File

object HomeAssistantService {

    private const val TOPIC = "Selfhome"
    private const val UUID = "$TOPIC-ae43-5001-eda2-0d66b3b1686"// TODO "selfhome-d8e1-40ef-9e59-0d66b3bc525e"

    fun startService(ipBroker: String, portBroker: Int, settingsPath: File, controller: Controller): HomeAssistant {
        val uriBroker = "tcp://$ipBroker:$portBroker"
        val mqttClient = MqttClient(uriBroker, UUID)

        val ha = HomeAssistant(mqttClient, controller).apply {
            this.connect()
            this.subscribe(TOPIC)
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
                        if(key == "qos") {
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