package com.redred.treadmillvertiintervallcalc.model

import kotlin.math.roundToInt

data class WorkoutPlan(
    val targetElevationMeters: Double,
    val totalDurationMinutes: Int,
    val segments: List<WorkoutSegment>
) {
    val plannedElevationMeters: Double
        get() = segments.lastOrNull()?.cumulativeElevationMeters ?: 0.0

    val targetDifferenceMeters: Double
        get() = plannedElevationMeters - targetElevationMeters

    val averageElevationPerMinute: Double
        get() = if (totalDurationMinutes > 0) plannedElevationMeters / totalDurationMinutes else 0.0

    val totalDistanceKilometers: Double
        get() = segments.sumOf { segment ->
            if (segment.paceMinutesPerKm > 0.0) {
                segment.durationMinutes / segment.paceMinutesPerKm
            } else {
                0.0
            }
        }

    val averagePaceMinutesPerKm: Double
        get() = if (totalDistanceKilometers > 0.0) totalDurationMinutes / totalDistanceKilometers else 0.0

    val completedSegments: Int
        get() = segments.count { it.isCompleted }

    val completedDurationMinutes: Int
        get() = segments.filter { it.isCompleted }.sumOf { it.durationMinutes }

    val completedElevationMeters: Double
        get() = segments.filter { it.isCompleted }.sumOf { it.elevationMeters }

    val remainingElevationMeters: Double
        get() = (plannedElevationMeters - completedElevationMeters).coerceAtLeast(0.0)

    fun toAppleNotesText(): String {
        var startMinute = 0
        val rows = segments.joinToString(separator = "\n") { segment ->
            val endMinute = startMinute + segment.durationMinutes
            val checked = if (segment.isCompleted) "x" else " "
            val line = "[$checked] $startMinute-$endMinute min - ${segment.title} - " +
                "${segment.pace}/km @ ${formatIncline(segment.inclinePercent)}% - " +
                "ca. ${segment.elevationMeters.roundToInt()} Hm"
            startMinute = endMinute
            line
        }

        return "VertiRun Workout - $totalDurationMinutes min / ca. ${targetElevationMeters.roundToInt()} Hm\n\n" +
            rows +
            "\n\nTotal: ca. ${plannedElevationMeters.roundToInt()} Hm"
    }
}

private fun formatIncline(value: Double): String {
    val roundedTenths = (value * 10).roundToInt()
    return if (roundedTenths % 10 == 0) {
        (roundedTenths / 10).toString()
    } else {
        val whole = roundedTenths / 10
        val decimal = kotlin.math.abs(roundedTenths % 10)
        "$whole.$decimal"
    }
}
