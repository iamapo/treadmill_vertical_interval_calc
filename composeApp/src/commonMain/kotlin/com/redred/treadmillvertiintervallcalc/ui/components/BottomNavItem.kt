package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import vertirun.composeapp.generated.resources.Res
import vertirun.composeapp.generated.resources.vr_ic_calendar_planer

@Composable
internal fun BottomNavItem(
    label: String,
    icon: DrawableResource,
    selected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val color = when {
        selected -> VertiGreen
        enabled -> Color(0xFF9BA1AA)
        else -> Color(0xFFC6CBD0)
    }

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(18.dp))
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(width = 64.dp, height = 30.dp)
                .clip(VertiPillShape)
                .background(
                    if (selected) {
                        Brush.horizontalGradient(listOf(VertiGreenSoft, Color(0xFFF4FAF6)))
                    } else {
                        Brush.horizontalGradient(listOf(Color.Transparent, Color.Transparent))
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            VrIcon(resource = icon, contentDescription = null, size = 24.dp, tint = color)
        }
        Text(
            text = label,
            color = color,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Preview
@Composable
private fun BottomNavItemPreview() {
    PreviewSurface {
        BottomNavItem(
            label = "Planer",
            icon = Res.drawable.vr_ic_calendar_planer,
            selected = true,
            enabled = true,
            onClick = {}
        )
    }
}
