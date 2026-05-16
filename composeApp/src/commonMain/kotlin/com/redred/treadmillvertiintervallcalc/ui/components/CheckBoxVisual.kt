package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import verticaltreadmillrun.composeapp.generated.resources.Res
import verticaltreadmillrun.composeapp.generated.resources.vr_ic_check_circle

@Composable
internal fun CheckBoxVisual(
    checked: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(34.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(if (checked) VertiGreen else Color.White)
            .clickable(onClick = onClick)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            VrIcon(
                resource = Res.drawable.vr_ic_check_circle,
                contentDescription = null,
                size = 22.dp,
                tint = Color.White
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(26.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(Color.Transparent)
                    .border(androidx.compose.foundation.BorderStroke(2.dp, Color(0xFF8F949E)), RoundedCornerShape(3.dp))
            )
        }
    }
}

@Preview
@Composable
private fun CheckBoxVisualPreview() {
    PreviewSurface {
        CheckBoxVisual(checked = true, onClick = {})
    }
}
