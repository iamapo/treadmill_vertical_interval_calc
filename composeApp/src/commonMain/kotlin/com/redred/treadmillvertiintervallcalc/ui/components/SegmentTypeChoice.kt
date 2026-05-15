package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redred.treadmillvertiintervallcalc.presentation.ManualSegmentType
import vertirun.composeapp.generated.resources.Res
import vertirun.composeapp.generated.resources.vr_ic_check_circle
import vertirun.composeapp.generated.resources.vr_ic_chevron_right

@Composable
internal fun SegmentTypeChoice(
    type: ManualSegmentType,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        color = if (selected) Color(0xFFF4FBF7) else Color.White,
        border = BorderStroke(if (selected) 2.dp else 1.dp, if (selected) VertiGreen else VertiLine),
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            VrIcon(
                resource = manualSegmentIconResource(type),
                contentDescription = null,
                size = 32.dp,
                tint = VertiGreen
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 18.dp)
            ) {
                Text(text = manualSegmentTypeLabel(type), color = VertiText, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(text = manualSegmentTypeDescription(type), color = VertiMuted, style = MaterialTheme.typography.bodyMedium)
            }
            VrIcon(
                resource = if (selected) Res.drawable.vr_ic_check_circle else Res.drawable.vr_ic_chevron_right,
                contentDescription = null,
                size = 24.dp,
                tint = if (selected) VertiGreen else VertiMuted
            )
        }
    }
}

@Preview
@Composable
private fun SegmentTypeChoicePreview() {
    PreviewSurface {
        SegmentTypeChoice(
            type = ManualSegmentType.REPETITION,
            selected = true,
            onClick = {}
        )
    }
}
