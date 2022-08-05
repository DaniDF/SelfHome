package model

abstract class SmartDriver {
    abstract fun retrieveState() : SmartState

    abstract fun setState() : SmartState
}