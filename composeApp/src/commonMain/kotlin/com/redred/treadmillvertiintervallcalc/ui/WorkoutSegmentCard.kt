package com.redred.treadmillvertiintervallcalc.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redred.treadmillvertiintervallcalc.model.WorkoutSegment
import org.jetbrains.compose.resources.stringResource
import vertirun.composeapp.generated.resources.*

@Composable
fun WorkoutSegmentCard(
    segment: WorkoutSegment,
    startMinute: Int,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier
) {
    val endMinute = startMinute + segment.durationMinutes
    val containerColor by animateColorAsState(
        targetValue = if (segment.isCompleted) {
            MaterialTheme.colorScheme.surfaceVariant
        } else {
            MaterialTheme.colorScheme.surface
        }
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .alpha(if (segment.isCompleted) 0.62f else 1f),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (onCheckedChange != null) {
                Checkbox(
                    checked = segment.isCompleted,
                    onCheckedChange = onCheckedChange
                )
                Spacer(Modifier.width(8.dp))
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "$startMinute-$endMinute min | ${segment.title}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${segment.durationMinutes} min | ${segment.pace}/km | " +
                        "${inclineText(segment.inclinePercent)} | " +
                        "${metersText(segment.elevationMeters)} | " +
                        "${stringResource(Res.string.segment_total)} ${metersText(segment.cumulativeElevationMeters)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview
@Composable
private fun WorkoutSegmentCardPreview() {
    PreviewSurface {
        WorkoutSegmentCard(
            segment = PreviewWorkoutData.hardSegment,
            startMinute = 15,
            onCheckedChange = {},
            modifier = Modifier.previewCardPadding()
        )
    }
}

@Preview
@Composable
private fun CompletedWorkoutSegmentCardPreview() {
    PreviewSurface {
        WorkoutSegmentCard(
            segment = PreviewWorkoutData.completedSegment,
            startMinute = 15,
            onCheckedChange = {},
            modifier = Modifier.previewCardPadding()
        )
    }
}
