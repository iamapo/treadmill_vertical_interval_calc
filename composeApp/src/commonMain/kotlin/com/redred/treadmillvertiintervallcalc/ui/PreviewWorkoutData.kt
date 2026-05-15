package com.redred.treadmillvertiintervallcalc.ui

import com.redred.treadmillvertiintervallcalc.ui.components.*

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.redred.treadmillvertiintervallcalc.model.SegmentType
import com.redred.treadmillvertiintervallcalc.model.WorkoutPlan
import com.redred.treadmillvertiintervallcalc.model.WorkoutSegment

@Composable
internal fun PreviewSurface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = VertiGreen,
            primaryContainer = VertiGreenSoft,
            secondary = VertiGreen,
            background = VertiPage,
            surface = androidx.compose.ui.graphics.Color.White,
            onPrimary = androidx.compose.ui.graphics.Color.White,
            onPrimaryContainer = VertiText,
            onBackground = VertiText,
            onSurface = VertiText
        )
    ) {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}

internal object PreviewWorkoutData {
    val hardSegment = WorkoutSegment(
        id = "preview-hard-1",
        title = "Hard interval 1",
        durationMinutes = 5,
        pace = "8:30",
        paceMinutesPerKm = 8.5,
        inclinePercent = 15.0,
        elevationMeters = 88.2,
        cumulativeElevationMeters = 143.5,
        type = SegmentType.HARD_INTERVAL
    )

    val completedSegment = hardSegment.copy(isCompleted = true)

    val plan = WorkoutPlan(
        targetElevationMeters = 1200.0,
        totalDurationMinutes = 120,
        segments = listOf(
            WorkoutSegment(
                id = "preview-warmup",
                title = "Warm-up",
                durationMinutes = 10,
                pace = "7:30",
                paceMinutesPerKm = 7.5,
                inclinePercent = 2.0,
                elevationMeters = 26.7,
                cumulativeElevationMeters = 26.7,
                type = SegmentType.WARMUP
            ),
            WorkoutSegment(
                id = "preview-climb",
                title = "Warm-up climb",
                durationMinutes = 5,
                pace = "7:30",
                paceMinutesPerKm = 7.5,
                inclinePercent = 4.0,
                elevationMeters = 26.7,
                cumulativeElevationMeters = 53.4,
                type = SegmentType.STEADY_CLIMB
            ),
            hardSegment.copy(cumulativeElevationMeters = 141.6),
            WorkoutSegment(
                id = "preview-recovery-1",
                title = "Recovery 1",
                durationMinutes = 3,
                pace = "9:30",
                paceMinutesPerKm = 9.5,
                inclinePercent = 5.0,
                elevationMeters = 15.8,
                cumulativeElevationMeters = 157.4,
                type = SegmentType.RECOVERY
            ),
            WorkoutSegment(
                id = "preview-hard-2",
                title = "Hard interval 2",
                durationMinutes = 5,
                pace = "8:30",
                paceMinutesPerKm = 8.5,
                inclinePercent = 15.0,
                elevationMeters = 88.2,
                cumulativeElevationMeters = 245.6,
                type = SegmentType.HARD_INTERVAL
            ),
            WorkoutSegment(
                id = "preview-steady",
                title = "Steady climb adjustment",
                durationMinutes = 4,
                pace = "8:30",
                paceMinutesPerKm = 8.5,
                inclinePercent = 11.5,
                elevationMeters = 54.1,
                cumulativeElevationMeters = 299.7,
                type = SegmentType.STEADY_CLIMB
            ),
            WorkoutSegment(
                id = "preview-cooldown",
                title = "Cool-down",
                durationMinutes = 10,
                pace = "8:30",
                paceMinutesPerKm = 8.5,
                inclinePercent = 0.0,
                elevationMeters = 0.0,
                cumulativeElevationMeters = 299.7,
                type = SegmentType.COOLDOWN
            )
        )
    )

    val checklistPlan = plan.copy(
        segments = plan.segments.mapIndexed { index, segment ->
            segment.copy(isCompleted = index < 3)
        }
    )
}

internal fun Modifier.previewCardPadding(): Modifier = padding(20.dp)
