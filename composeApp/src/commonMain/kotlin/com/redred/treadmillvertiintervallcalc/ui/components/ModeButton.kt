package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import verticaltreadmillrun.composeapp.generated.resources.Res
import verticaltreadmillrun.composeapp.generated.resources.vr_ic_spark

@Composable
internal fun ModeButton(
    icon: DrawableResource,
    label: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .height(46.dp)
            .clip(VertiPillShape)
            .clickable(onClick = onClick),
        shape = VertiPillShape,
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier.background(
                if (selected) {
                    VertiGreenBrush
                } else {
                    Brush.linearGradient(listOf(Color.Transparent, Color.Transparent))
                }
            ),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                VrIcon(
                    resource = icon,
                    contentDescription = null,
                    size = 22.dp,
                    tint = if (selected) Color.White else VertiText
                )
                Text(
                    text = label,
                    modifier = Modifier.padding(start = 8.dp),
                    color = if (selected) Color.White else VertiText,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                )
            }
        }
    }
}

@Preview
@Composable
private fun ModeButtonPreview() {
    PreviewSurface {
        ModeButton(
            icon = Res.drawable.vr_ic_spark,
            label = "Auto",
            selected = true,
            onClick = {}
        )
    }
}
