package model

import java.io.InputStream
import java.io.OutputStream

interface SmartDeviceConnection {
    fun getInput() : InputStream
    fun getOutput() : OutputStream
}