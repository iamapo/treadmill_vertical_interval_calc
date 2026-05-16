package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import verticaltreadmillrun.composeapp.generated.resources.Res
import verticaltreadmillrun.composeapp.generated.resources.vr_ic_mountain_logo

@Composable
internal fun MountainLogo() {
    VrIcon(
        resource = Res.drawable.vr_ic_mountain_logo,
        contentDescription = "VerticalTreadmillRun",
        size = 44.dp,
        tint = VertiGreen
    )
}

@Preview
@Composable
private fun MountainLogoPreview() {
    PreviewSurface {
        MountainLogo()
    }
}
