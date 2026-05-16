package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redred.treadmillvertiintervallcalc.model.WorkoutPlan
import org.jetbrains.compose.resources.stringResource
import verticaltreadmillrun.composeapp.generated.resources.Res
import verticaltreadmillrun.composeapp.generated.resources.progress_completed_elevation
import verticaltreadmillrun.composeapp.generated.resources.progress_completed_minutes
import verticaltreadmillrun.composeapp.generated.resources.progress_completed_segments
import verticaltreadmillrun.composeapp.generated.resources.progress_remaining_elevation
import verticaltreadmillrun.composeapp.generated.resources.progress_title
import verticaltreadmillrun.composeapp.generated.resources.vr_ic_check_circle
import verticaltreadmillrun.composeapp.generated.resources.vr_ic_clock
import verticaltreadmillrun.composeapp.generated.resources.vr_ic_mountain

@Composable
internal fun ChecklistProgressCard(plan: WorkoutPlan) {
    val progress = if (plan.segments.isEmpty()) 0f else plan.completedSegments.toFloat() / plan.segments.size

    VertiCard {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(VertiGreen),
                    contentAlignment = Alignment.Center
                ) {
                    VrIcon(
                        resource = Res.drawable.vr_ic_check_circle,
                        contentDescription = null,
                        size = 34.dp,
                        tint = Color.White
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                ) {
                    Text(
                        text = "Dein Workout ist bereit - bleib dran!",
                        color = VertiMuted,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                ProgressMetric(Res.drawable.vr_ic_check_circle, stringResource(Res.string.progress_completed_segments), "${plan.completedSegments} / ${plan.segments.size}", Modifier.weight(1f))
                ProgressMetric(Res.drawable.vr_ic_clock, stringResource(Res.string.progress_completed_minutes), "${plan.completedDurationMinutes} / ${plan.totalDurationMinutes}", Modifier.weight(1f))
                ProgressMetric(Res.drawable.vr_ic_mountain, stringResource(Res.string.progress_completed_elevation), metersText(plan.completedElevationMeters), Modifier.weight(1f))
                ProgressMetric(Res.drawable.vr_ic_mountain, stringResource(Res.string.progress_remaining_elevation), metersText(plan.remainingElevationMeters), Modifier.weight(1f), mutedIcon = true)
            }
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(VertiPillShape),
                color = VertiGreen,
                trackColor = Color(0xFFE9EEF0)
            )
            Text(
                text = "${(progress * 100).toInt()} %  abgeschlossen",
                modifier = Modifier.fillMaxWidth(),
                color = VertiText,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun ChecklistProgressCardPreview() {
    PreviewSurface {
        ChecklistProgressCard(plan = PreviewWorkoutData.checklistPlan)
    }
}
