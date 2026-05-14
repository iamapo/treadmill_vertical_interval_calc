package com.redred.treadmillvertiintervallcalc.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redred.treadmillvertiintervallcalc.model.WorkoutPlan
import org.jetbrains.compose.resources.stringResource
import vertirun.composeapp.generated.resources.*

@Composable
fun WorkoutGeneratedScreen(
    plan: WorkoutPlan,
    onOpenChecklist: () -> Unit,
    onCopyWorkoutText: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            WorkoutSummaryCard(plan = plan)
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onOpenChecklist,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(Res.string.button_start_checklist))
                }
                OutlinedButton(
                    onClick = onCopyWorkoutText,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(Res.string.button_copy_workout_text))
                }
            }
        }
        var startMinute = 0
        plan.segments.forEach { segment ->
            val segmentStart = startMinute
            item {
                WorkoutSegmentCard(
                    segment = segment,
                    startMinute = segmentStart,
                    onCheckedChange = null
                )
            }
            startMinute += segment.durationMinutes
        }
    }
}

@Preview
@Composable
private fun WorkoutGeneratedScreenPreview() {
    PreviewSurface {
        WorkoutGeneratedScreen(
            plan = PreviewWorkoutData.plan,
            onOpenChecklist = {},
            onCopyWorkoutText = {}
        )
    }
}
