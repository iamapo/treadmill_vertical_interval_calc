package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import verticaltreadmillrun.composeapp.generated.resources.Res
import verticaltreadmillrun.composeapp.generated.resources.vr_ic_mountain

@Composable
internal fun IconBubble(
    icon: DrawableResource,
    modifier: Modifier = Modifier,
    selected: Boolean = false
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (selected) VertiGreenSoft else androidx.compose.ui.graphics.Color(0xFFF1F6F3))
            .border(1.dp, VertiLine, RoundedCornerShape(10.dp))
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        VrIcon(
            resource = icon,
            contentDescription = null,
            modifier = Modifier.size(26.dp),
            tint = VertiGreen
        )
    }
}

@Preview
@Composable
private fun IconBubblePreview() {
    PreviewSurface {
        IconBubble(icon = Res.drawable.vr_ic_mountain, selected = true)
    }
}
