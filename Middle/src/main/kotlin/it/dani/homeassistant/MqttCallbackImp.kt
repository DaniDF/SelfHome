package it.dani.homeassistant

import org.eclipse.paho.mqttv5.client.IMqttToken
import org.eclipse.paho.mqttv5.client.MqttCallback
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse
import org.eclipse.paho.mqttv5.common.MqttException
import org.eclipse.paho.mqttv5.common.MqttMessage
import org.eclipse.paho.mqttv5.common.packet.MqttProperties

class MqttCallbackImp(private var debug: Boolean = false) : MqttCallback {
    val onMessage: MutableList<(String, String) -> Unit> = ArrayList()
    val onError: MutableList<(MqttException) -> Unit> = ArrayList()
    override fun disconnected(p0: MqttDisconnectResponse?) {

    }

    override fun mqttErrorOccurred(p0: MqttException?) {
        p0?.let {
            this.onError.forEach { it(p0) }
        }
    }

    override fun messageArrived(p0: String?, p1: MqttMessage?) {
        if (this.debug) {
            println("p0: $p0\np1: ${String(p1?.payload ?: ByteArray(1))}")
        }
        p0?.let {
            val payload = String(p1?.payload ?: ByteArray(0))
            this.onMessage.forEach { it(p0, payload) }
        }
    }

    override fun deliveryComplete(p0: IMqttToken?) {

    }

    override fun connectComplete(p0: Boolean, p1: String?) {

    }

    override fun authPacketArrived(p0: Int, p1: MqttProperties?) {

    }
}