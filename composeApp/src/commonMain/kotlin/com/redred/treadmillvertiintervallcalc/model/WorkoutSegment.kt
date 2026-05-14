package com.redred.treadmillvertiintervallcalc.model

data class WorkoutSegment(
    val id: String,
    val title: String,
    val durationMinutes: Int,
    val pace: String,
    val paceMinutesPerKm: Double,
    val inclinePercent: Double,
    val elevationMeters: Double,
    val cumulativeElevationMeters: Double,
    val type: SegmentType,
    val isCompleted: Boolean = false
)
