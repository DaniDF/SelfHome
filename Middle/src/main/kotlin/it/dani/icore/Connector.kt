package it.dani.icore

interface Connector {
    fun send(data: String, timeout: Int = 3000): String
}