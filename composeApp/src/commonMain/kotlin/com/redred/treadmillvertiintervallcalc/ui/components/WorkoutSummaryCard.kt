package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.redred.treadmillvertiintervallcalc.model.WorkoutPlan
import org.jetbrains.compose.resources.stringResource
import vertirun.composeapp.generated.resources.*

@Composable
fun WorkoutSummaryCard(
    plan: WorkoutPlan,
    modifier: Modifier = Modifier
) {
    SummaryCard(
        modifier = modifier,
        title = stringResource(Res.string.summary_title),
        rows = listOf(
            stringResource(Res.string.summary_target_elevation) to metersText(plan.targetElevationMeters),
            stringResource(Res.string.summary_planned_elevation) to "${metersText(plan.plannedElevationMeters)} (${signedMetersText(plan.targetDifferenceMeters)})",
            stringResource(Res.string.summary_total_duration) to "${plan.totalDurationMinutes} min",
            stringResource(Res.string.summary_total_distance) to kilometersText(plan.totalDistanceKilometers),
            stringResource(Res.string.summary_average_pace) to paceText(plan.averagePaceMinutesPerKm),
            stringResource(Res.string.summary_average_gain) to "${oneDecimalText(plan.averageElevationPerMinute)} m/min"
        )
    )
}

@Preview
@Composable
private fun WorkoutSummaryCardPreview() {
    PreviewSurface {
        WorkoutSummaryCard(
            plan = PreviewWorkoutData.plan,
            modifier = Modifier.previewCardPadding()
        )
    }
}
