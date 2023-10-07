package com.example.multicalculator

class Calculator {
    fun add(left:Int, right:Int):Int{
        return left + right
    }

    fun subtract(left: Int, right: Int): Int {
        return left - right
    }

    fun divide(left: Int, right: Int): Int {
        return left / right
    }

    fun multiply(left: Int, right: Int): Int {
        return left * right
    }
}
fun main() {
    val runCalculator = Calculator()

    println(runCalculator.add(5, 3))
    println(runCalculator.subtract(5, 3))
    println(runCalculator.divide(6, 3))
    println(runCalculator.multiply(5, 3))

}
