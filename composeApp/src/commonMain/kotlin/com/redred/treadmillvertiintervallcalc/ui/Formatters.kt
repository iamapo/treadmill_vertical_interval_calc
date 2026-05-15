package com.redred.treadmillvertiintervallcalc.ui

import kotlin.math.abs
import kotlin.math.roundToInt

fun metersText(value: Double): String = "${value.roundToInt()} m"

fun signedMetersText(value: Double): String {
    val rounded = value.roundToInt()
    val sign = if (rounded >= 0) "+" else "-"
    return "$sign${abs(rounded)} m"
}

fun inclineText(value: Double): String {
    val roundedTenths = (value * 10).roundToInt()
    return if (roundedTenths % 10 == 0) {
        "${roundedTenths / 10}%"
    } else {
        val whole = roundedTenths / 10
        val decimal = abs(roundedTenths % 10)
        "$whole.$decimal%"
    }
}

fun oneDecimalText(value: Double): String {
    val roundedTenths = (value * 10).roundToInt()
    val whole = roundedTenths / 10
    val decimal = abs(roundedTenths % 10)
    return "$whole.$decimal"
}

fun kilometersText(value: Double): String = "${oneDecimalText(value)} km"

fun paceText(minutesPerKm: Double): String {
    val totalSeconds = (minutesPerKm * 60.0).roundToInt()
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "$minutes:${seconds}"
}
