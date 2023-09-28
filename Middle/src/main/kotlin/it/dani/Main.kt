package it.dani

import com.google.gson.GsonBuilder
import it.dani.homeassistant.HomeAssistantService
import it.dani.icontroller.Controller
import it.dani.listener.Listener
import it.dani.listener.RequestListener
import it.dani.selfhome.SelfHomeConnector
import it.dani.selfhome.SelfHomeController
import it.dani.way.WhereAreYouListener
import java.io.File
import java.net.InetAddress

fun main(args: Array<String>) {
    var settings: Map<String, String> = mapOf(
        "listening_port" to "4000",
        "selfhome_address" to "localhost",
        "selfhome_port" to "4001",
        "broker_address" to "localhost",
        "broker_port" to "1883",
        "settings_path" to ".",
        "way_net" to "229.255.255.250",
        "way_port" to "4444"
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


    val listeningPort = settings["listening_port"]!!.toFloat().toInt()

    val selfHomeCoreAddress = InetAddress.getByName(settings["selfhome_address"])
    val selfHomeCorePort = settings["selfhome_port"]!!.toFloat().toInt()

    val brokerAddress = settings["broker_address"]!!
    val brokerPort = settings["broker_port"]!!.toFloat().toInt()
    val settingsPath = File(settings["settings_path"]!!)

    val wayNet = InetAddress.getByName(settings["way_net"])
    val wayPort = settings["way_port"]!!.toFloat().toInt()

    val controller: Controller = SelfHomeController(
        SelfHomeConnector(selfHomeCoreAddress, selfHomeCorePort)
    )

    val listeners: List<Listener> = listOf(
        RequestListener(listeningPort, controller),
        WhereAreYouListener(wayNet, wayPort, listeningPort)
    ).also { l -> l.forEach{ it.start() } }

    HomeAssistantService.startService(brokerAddress, brokerPort, settingsPath, controller).also { controller.getDevices() }

    Thread.currentThread().join()

    listeners.forEach { it.stop() }
}

fun helper(): String {
    return "Usage -c <config_file>"
}