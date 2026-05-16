package com.redred.treadmillvertiintervallcalc.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import androidx.compose.foundation.Image
import com.redred.treadmillvertiintervallcalc.ui.PreviewSurface
import verticaltreadmillrun.composeapp.generated.resources.Res
import verticaltreadmillrun.composeapp.generated.resources.vr_ic_mountain_logo

@Composable
internal fun VrIcon(
    resource: DrawableResource,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    tint: Color? = VertiGreen
) {
    Image(
        painter = painterResource(resource),
        contentDescription = contentDescription,
        modifier = modifier.size(size),
        colorFilter = tint?.let(ColorFilter::tint)
    )
}

@Preview
@Composable
private fun VrIconPreview() {
    PreviewSurface {
        VrIcon(
            resource = Res.drawable.vr_ic_mountain_logo,
            contentDescription = "Logo"
        )
    }
}
