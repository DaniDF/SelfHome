package model

fun main(args : Array<String>) {
    //val actions = setOf<SmartAction>(SmartAction(0x01A,"Turn on",))

    val smartDevice = StatefulSmartDevice(0x01D,"Luce calda 1", setOf("Calda","Luce calda"), emptySet(),false, ValueSmartState(0))
}