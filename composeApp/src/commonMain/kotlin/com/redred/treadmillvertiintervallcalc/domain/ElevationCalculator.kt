package com.redred.treadmillvertiintervallcalc.domain

import kotlin.math.round
import kotlin.math.roundToInt

object ElevationCalculator {
    fun elevationMeters(
        durationMinutes: Int,
        paceMinutesPerKm: Double,
        inclinePercent: Double
    ): Double {
        if (durationMinutes <= 0 || paceMinutesPerKm <= 0.0 || inclinePercent <= 0.0) return 0.0
        return durationMinutes * 10.0 * inclinePercent / paceMinutesPerKm
    }

    fun roundInclineToStep(inclinePercent: Double, stepPercent: Double): Double {
        if (stepPercent <= 0.0) return inclinePercent
        return round(inclinePercent / stepPercent) * stepPercent
    }

    fun oneDecimal(value: Double): Double = (value * 10.0).roundToInt() / 10.0
}
