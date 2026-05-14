package com.redred.treadmillvertiintervallcalc.model

data class WorkoutInput(
    val targetElevationMeters: Double,
    val totalDurationMinutes: Int,
    val warmUpDurationMinutes: Int,
    val coolDownDurationMinutes: Int,
    val maxInclinePercent: Double,
    val inclineStepPercent: Double,
    val hardIntervalDurationMinutes: Int,
    val recoveryIntervalDurationMinutes: Int,
    val hardIntervalPace: String,
    val hardIntervalPaceMinutesPerKm: Double,
    val recoveryPace: String,
    val recoveryPaceMinutesPerKm: Double,
    val warmUpPace: String,
    val warmUpPaceMinutesPerKm: Double,
    val coolDownPace: String,
    val coolDownPaceMinutesPerKm: Double,
    val preferredHardInclinePercent: Double,
    val preferredRecoveryInclinePercent: Double
)
