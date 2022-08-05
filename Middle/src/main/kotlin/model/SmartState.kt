package model

import java.net.URL

interface SmartState

data class ValueSmartState(val value : Int) : SmartState

data class ImageSmartState(val value : Any) : SmartState    //TODO non Any ma un qualche oggetto da pensare

data class VideoSmartState(val value : Any) : SmartState

data class StreamSmartState(val value : URL) : SmartState