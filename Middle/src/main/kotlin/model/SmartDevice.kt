package model

abstract class SmartDevice(
    open val id : Int,
    open var commonName : String,
    open val alternativeNames : Set<String> = emptySet(),
    open val actions : Set<SmartAction> = emptySet(),
    open var readOnly : Boolean
)

class StatefulSmartDevice(
    override val id : Int,
    override var commonName : String,
    override val alternativeNames : Set<String> = emptySet(),
    override val actions : Set<SmartAction> = emptySet(),
    override var readOnly : Boolean,
    var state : SmartState
) : SmartDevice(id,commonName,alternativeNames, actions, readOnly)

class StatelessSmartDevice(
    override val id : Int,
    override var commonName : String,
    override val alternativeNames : Set<String> = emptySet(),
    override val actions : Set<SmartAction> = emptySet(),
    override var readOnly : Boolean,
    var memorizedState : SmartState
) : SmartDevice(id,commonName,alternativeNames, actions, readOnly)
