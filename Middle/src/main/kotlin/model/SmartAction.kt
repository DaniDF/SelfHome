package model

import java.time.LocalDateTime

abstract class SmartAction(
    open val id : Int,
    open val commonName : String,
    open val allowedStates : List<SmartState> = emptyList(),
    open val driver : SmartDriver
) {
    fun getComputedSmartAction(futureState : SmartState) : ComputedStartState {
        TODO("Not yet implemented")
    }
}

abstract class ContinuousSmartAction (
    id : Int,
    commonName : String,
    allowedStates : List<SmartState> = emptyList(),
    driver : SmartDriver
) : SmartAction(id, commonName, allowedStates, driver)

abstract class DiscreteSmartAction(
    id : Int,
    commonName : String,
    allowedStates : List<SmartState> = emptyList(),
    driver : SmartDriver
) : SmartAction(id, commonName, allowedStates, driver)

abstract class PulseSmartAction(
    id : Int,
    commonName : String,
    allowedStates : List<SmartState> = emptyList(),
    driver : SmartDriver
) : DiscreteSmartAction(id, commonName, allowedStates, driver)

data class ComputedStartState (
    val timestamp : LocalDateTime,
    val futureStateIndex : Int,
    val smartActionId : Int
)