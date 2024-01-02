package it.dani.homeassistant

import org.eclipse.paho.mqttv5.client.IMqttToken
import org.eclipse.paho.mqttv5.client.MqttClient
import org.eclipse.paho.mqttv5.common.MqttException
import org.eclipse.paho.mqttv5.common.MqttMessage

class MqttClientResilient(serverURI: String, clientId: String) : MqttClient(serverURI, clientId) {

    private val topics: MutableSet<Pair<String, Int>> = HashSet()
    override fun publish(topic: String?, message: MqttMessage?) {
        var flagStop = false
        var count = 2

        while(!flagStop && count > 0) {
            try {
                super.publish(topic, message)
                flagStop = true
            } catch (e: MqttException) {
                super.reconnect()
                this.topics.forEach { t ->
                    this.subscribe(t.first, t.second)
                }
                count--
            }
        }
    }

    override fun subscribe(topicFilters: Array<out String>?, qos: IntArray?): IMqttToken {
        val result = super.subscribe(topicFilters, qos)

        topicFilters?.forEachIndexed { index, topic ->
            this.topics += topic to (qos?.get(index) ?: 0)
        }

        return result
    }

    override fun unsubscribe(topicFilters: Array<out String>?) {
        val result = super.unsubscribe(topicFilters)

        topicFilters?.forEach { topic ->
            this.topics -= this.topics.filter { x -> x.first == topic}.toSet()
        }

        return result
    }
}