package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redred.treadmillvertiintervallcalc.model.WorkoutSegment
import vertirun.composeapp.generated.resources.Res
import vertirun.composeapp.generated.resources.vr_ic_check_circle
import vertirun.composeapp.generated.resources.vr_ic_chevron_down

@Composable
internal fun ChecklistSegmentRow(
    segment: WorkoutSegment,
    onToggle: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(if (segment.isCompleted) 0.82f else 1f),
        shape = RoundedCornerShape(10.dp),
        color = if (segment.isCompleted) Color(0xFFF5FBF7) else Color.White,
        border = BorderStroke(1.dp, if (segment.isCompleted) Color(0xFFD1E6D8) else VertiLine),
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CheckBoxVisual(checked = segment.isCompleted, onClick = onToggle)
            VrIcon(
                resource = segmentIconResource(segment.type),
                contentDescription = null,
                modifier = Modifier.padding(start = 18.dp),
                size = 28.dp,
                tint = VertiGreen
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 18.dp)
            ) {
                Text(
                    text = localizedSegmentTitle(segment),
                    color = VertiText,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textDecoration = if (segment.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                )
                Text(
                    text = "${segment.durationMinutes} min  •  ${segment.pace} min/km  •  ${inclineText(segment.inclinePercent)}",
                    color = VertiText,
                    style = MaterialTheme.typography.bodyMedium,
                    textDecoration = if (segment.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                )
            }
            if (segment.isCompleted) {
                VrIcon(
                    resource = Res.drawable.vr_ic_check_circle,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 16.dp),
                    size = 24.dp,
                    tint = VertiGreen
                )
            }
        }
    }
}

@Preview
@Composable
private fun ChecklistSegmentRowPreview() {
    PreviewSurface {
        ChecklistSegmentRow(
            segment = PreviewWorkoutData.completedSegment,
            onToggle = {}
        )
    }
}
