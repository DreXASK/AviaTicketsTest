package com.drexask.aviatickets.presentation.utils

import android.content.Context
import android.util.TypedValue

fun convertDpToPx(context: Context, dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}