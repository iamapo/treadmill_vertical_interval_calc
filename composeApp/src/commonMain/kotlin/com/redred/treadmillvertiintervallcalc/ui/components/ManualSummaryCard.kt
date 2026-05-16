package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redred.treadmillvertiintervallcalc.presentation.PlanningMode
import com.redred.treadmillvertiintervallcalc.presentation.WorkoutPlannerState
import org.jetbrains.compose.resources.stringResource
import verticaltreadmillrun.composeapp.generated.resources.Res
import verticaltreadmillrun.composeapp.generated.resources.summary_average_pace
import verticaltreadmillrun.composeapp.generated.resources.summary_planned_elevation
import verticaltreadmillrun.composeapp.generated.resources.summary_title
import verticaltreadmillrun.composeapp.generated.resources.summary_total_distance
import verticaltreadmillrun.composeapp.generated.resources.summary_total_duration
import verticaltreadmillrun.composeapp.generated.resources.vr_ic_clipboard
import verticaltreadmillrun.composeapp.generated.resources.vr_ic_clock
import verticaltreadmillrun.composeapp.generated.resources.vr_ic_mountain
import verticaltreadmillrun.composeapp.generated.resources.vr_ic_route
import verticaltreadmillrun.composeapp.generated.resources.vr_ic_speedometer

@Composable
internal fun ManualSummaryCard(state: WorkoutPlannerState) {
    VertiCard {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                VrIcon(resource = Res.drawable.vr_ic_clipboard, contentDescription = null, size = 24.dp, tint = VertiGreen)
                Text(
                    text = stringResource(Res.string.summary_title).replace("Workout-", ""),
                    modifier = Modifier.padding(start = 10.dp),
                    color = VertiText,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, VertiLine, RoundedCornerShape(10.dp))
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ManualMetric(Res.drawable.vr_ic_clock, stringResource(Res.string.summary_total_duration), "${state.manualTotalDurationMinutes} min", Modifier.weight(1f))
                ManualMetric(Res.drawable.vr_ic_route, stringResource(Res.string.summary_total_distance), kilometersText(state.manualTotalDistanceKilometers), Modifier.weight(1f))
                ManualMetric(Res.drawable.vr_ic_speedometer, stringResource(Res.string.summary_average_pace), paceText(state.manualAveragePaceMinutesPerKm), Modifier.weight(1f))
                ManualMetric(Res.drawable.vr_ic_mountain, stringResource(Res.string.summary_planned_elevation), metersText(state.manualElevationMeters), Modifier.weight(1f))
            }
        }
    }
}

@Preview
@Composable
private fun ManualSummaryCardPreview() {
    PreviewSurface {
        ManualSummaryCard(
            WorkoutPlannerState(
                planningMode = PlanningMode.MANUAL,
                manualTotalDurationMinutes = 68,
                manualTotalDistanceKilometers = 11.2,
                manualAveragePaceMinutesPerKm = 5.75,
                manualElevationMeters = 540.0
            )
        )
    }
}
