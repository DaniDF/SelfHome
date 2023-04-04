package model

import java.io.File
import java.net.URL

interface SmartState<T> {
    fun getStateValue() : T
}

data class ValueSmartState(private val value : Int, val name : String) : SmartState<Int> {
    override fun getStateValue(): Int {
        return this.value
    }
}

data class FileSmartState(private val value : File) : SmartState<Any> {
    override fun getStateValue(): Any {
        return this.value
    }
}

data class StreamSmartState(private val value : URL) : SmartState<URL> {
    override fun getStateValue(): URL {
        return this.value
    }
}