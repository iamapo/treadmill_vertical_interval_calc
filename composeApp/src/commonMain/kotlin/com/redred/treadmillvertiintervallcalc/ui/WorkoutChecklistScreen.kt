package com.redred.treadmillvertiintervallcalc.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redred.treadmillvertiintervallcalc.model.WorkoutPlan

@Composable
fun WorkoutChecklistScreen(
    plan: WorkoutPlan,
    onToggleSegmentCompleted: (String) -> Unit,
    onResetChecklist: () -> Unit,
    onCopyWorkoutText: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            ChecklistProgressCard(plan = plan)
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onResetChecklist,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Reset checklist")
                }
                Button(
                    onClick = onCopyWorkoutText,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Copy workout text")
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
                    onCheckedChange = { onToggleSegmentCompleted(segment.id) }
                )
            }
            startMinute += segment.durationMinutes
        }
    }
}

@Composable
private fun ChecklistProgressCard(plan: WorkoutPlan) {
    val progress = if (plan.segments.isEmpty()) {
        0f
    } else {
        plan.completedSegments.toFloat() / plan.segments.size
    }

    SummaryCard(
        title = "Checklist progress",
        rows = listOf(
            "Completed segments" to "${plan.completedSegments}/${plan.segments.size}",
            "Completed minutes" to "${plan.completedDurationMinutes}/${plan.totalDurationMinutes} min",
            "Completed elevation" to metersText(plan.completedElevationMeters),
            "Remaining elevation" to metersText(plan.remainingElevationMeters)
        ),
        footer = {
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}

@Preview
@Composable
private fun WorkoutChecklistScreenPreview() {
    PreviewSurface {
        WorkoutChecklistScreen(
            plan = PreviewWorkoutData.checklistPlan,
            onToggleSegmentCompleted = {},
            onResetChecklist = {},
            onCopyWorkoutText = {}
        )
    }
}
