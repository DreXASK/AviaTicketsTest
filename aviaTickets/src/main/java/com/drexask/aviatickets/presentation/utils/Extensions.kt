package com.drexask.aviatickets.presentation.utils

import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable

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

inline fun <reified T : java.io.Serializable> Bundle.serializable(key: String): T? = when {
    SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializable(key) as? T
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}