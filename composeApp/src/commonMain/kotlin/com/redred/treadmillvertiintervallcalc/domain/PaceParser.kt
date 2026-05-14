package com.redred.treadmillvertiintervallcalc.domain

object PaceParser {
    fun parseMinutesPerKm(input: String): Double? {
        val parts = input.trim().split(":")
        if (parts.size != 2 || parts[1].length != 2) return null

        val minutes = parts[0].toIntOrNull() ?: return null
        val seconds = parts[1].toIntOrNull() ?: return null

        if (minutes <= 0 || seconds !in 0..59) return null
        return minutes + seconds / 60.0
    }

    fun isValid(input: String): Boolean = parseMinutesPerKm(input) != null
}
