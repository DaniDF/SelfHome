package model

abstract class SmartDevice(
    val id : Int,
    var commonName : String,
    var readOnly : Boolean,
    var connection: SmartDeviceConnection,
    val alternativeNames : Set<String> = emptySet(),
    var actions : Set<SmartAction<*>> = emptySet()
)

class StatefulSmartDevice <A,out T : SmartState<A>>(
    id : Int,
    commonName : String,
    readOnly : Boolean,
    connection: SmartDeviceConnection,
    var state : @UnsafeVariance T,
    alternativeNames : Set<String> = emptySet(),
    actions : Set<SmartAction<A>> = emptySet()
) : SmartDevice(id,commonName, readOnly, connection,alternativeNames, actions)

class StatelessSmartDevice <A,out T : SmartState<A>>(
    id : Int,
    commonName : String,
    readOnly : Boolean,
    connection: SmartDeviceConnection,
    var memorizedState : @UnsafeVariance T,
    alternativeNames : Set<String> = emptySet(),
    actions : Set<SmartAction<A>> = emptySet()
) : SmartDevice(id,commonName, readOnly, connection,alternativeNames, actions)
