package model

import java.io.Serializable
import java.time.LocalDateTime

abstract class SmartAction<T>(
    val id : Int,
    protected val device: SmartDevice,
    val commonName : String,
    protected open val driver : SmartDriver,
    val allowedStates : List<SmartState<T>> = emptyList()
) {
    open fun execute(state: SmartState<*>) : Any? {
        return this.driver.execute(this.device, listOf(state))
    }

    fun getComputedSmartAction(futureState : SmartState<*>) : ComputedStartState {
        TODO("Not yet implemented")
    }
}

open class ContinuousSmartAction <T> (
    id : Int,
    device : SmartDevice,
    commonName : String,
    driver : SmartDriver,
    allowedStates : List<SmartState<T>> = emptyList()
) : SmartAction<T>(id, device, commonName, driver, allowedStates)

open class DiscreteSmartAction <T>(
    id : Int,
    device : SmartDevice,
    commonName : String,
    driver : SmartDriver,
    allowedStates : List<SmartState<T>> = emptyList()
) : SmartAction<T>(id, device, commonName, driver, allowedStates)

open class PulseSmartAction <T>(
    id : Int,
    device : SmartDevice,
    commonName : String,
    driver : SmartDriver,
    allowedStates : List<SmartState<T>> = emptyList()
) : DiscreteSmartAction<T>(id, device, commonName, driver, allowedStates)

data class ComputedStartState (
    val timestamp : LocalDateTime,
    val futureStateIndex : Int,
    val smartActionId : Int
) : Serializable