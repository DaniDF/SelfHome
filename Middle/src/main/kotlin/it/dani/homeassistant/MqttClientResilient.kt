package it.dani.homeassistant

import org.eclipse.paho.mqttv5.client.MqttClient
import org.eclipse.paho.mqttv5.common.MqttException
import org.eclipse.paho.mqttv5.common.MqttMessage

class MqttClientResilient(serverURI: String, clientId: String) : MqttClient(serverURI, clientId) {
    override fun publish(topic: String?, message: MqttMessage?) {
        var flagStop = false
        var count = 2

        while(!flagStop && count > 0) {
            try {
                super.publish(topic, message)
                flagStop = true
            } catch (e: MqttException) {
                super.reconnect()
                count--
            }
        }
    }
}