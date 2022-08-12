package model

interface SmartDriver {
    /**
     * @throws DriverParameterException
     */
    fun execute(device : SmartDevice, args : List<Any>) : Any
}