package it.dani

import com.google.gson.GsonBuilder
import it.dani.controller.TestController
import it.dani.homeassistant.HomeAssistantService
import it.dani.icontroller.Controller
import java.io.File
import java.util.UUID

fun main(args: Array<String>) {
    var settings: Map<String, String> = mapOf(
        "broker_address" to "localhost",
        "broker_port" to "1883",
        "settings_path" to ".",
    )

    when(args.size) {
        2 -> {
            if(args[0] == "-c") {
                with(File(args[1]).reader()) {
                    val gson = GsonBuilder().create()
                    settings = (gson.fromJson(this, Map::class.java) as Map<*, *>).map { (key, value) ->
                        key.toString() to value.toString()
                    }.toMap()
                }
            } else {
                error(helper())
            }
        }
        else -> error(helper())
    }

    val brokerAddress = settings["broker_address"]!!
    val brokerPort = settings["broker_port"]!!.toFloat().toInt()
    val settingsPath = File(settings["settings_path"]!!)

    val controller: Controller = TestController()

    HomeAssistantService.startService(brokerAddress, brokerPort, settingsPath, controller, topic = "Test", uuid = UUID.randomUUID().toString())
        .also { controller.getDevices() }
}