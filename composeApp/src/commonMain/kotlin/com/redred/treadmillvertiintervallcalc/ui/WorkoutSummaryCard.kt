package com.redred.treadmillvertiintervallcalc.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

@Composable
fun SummaryCard(
    title: String,
    rows: List<Pair<String, String>>,
    modifier: Modifier = Modifier,
    footer: @Composable (() -> Unit)? = null
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            rows.forEach { (label, value) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = value,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            footer?.invoke()
        }
    }
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
