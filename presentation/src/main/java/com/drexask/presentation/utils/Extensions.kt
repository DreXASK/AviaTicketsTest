package com.drexask.presentation.utils

fun String.applyPriceDecorator(): String {
    val stringBuilder = StringBuilder()

    var counter = 1
    for (i in this.length-1 downTo 0 ) {
        stringBuilder.insert(0, this[i])
        if (counter == 3 && i != 0) {
            stringBuilder.insert(0, " ")
            counter = 0
        }
        counter++
    }

    return stringBuilder.toString()
}