package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redred.treadmillvertiintervallcalc.presentation.PlanningMode
import org.jetbrains.compose.resources.stringResource
import vertirun.composeapp.generated.resources.Res
import vertirun.composeapp.generated.resources.mode_auto
import vertirun.composeapp.generated.resources.mode_manual
import vertirun.composeapp.generated.resources.vr_ic_pencil
import vertirun.composeapp.generated.resources.vr_ic_spark

@Composable
internal fun ModeSegmentedControl(
    selectedMode: PlanningMode,
    onSelectPlanningMode: (PlanningMode) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(VertiPillShape)
            .border(1.dp, Color(0xFFD0DED6), VertiPillShape)
            .background(Color(0xFFF8FBF9))
            .padding(3.dp),
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        ModeButton(Res.drawable.vr_ic_spark, stringResource(Res.string.mode_auto), selectedMode == PlanningMode.AUTO, Modifier.weight(1f)) {
            onSelectPlanningMode(PlanningMode.AUTO)
        }
        ModeButton(Res.drawable.vr_ic_pencil, stringResource(Res.string.mode_manual), selectedMode == PlanningMode.MANUAL, Modifier.weight(1f)) {
            onSelectPlanningMode(PlanningMode.MANUAL)
        }
    }
}

@Preview
@Composable
private fun ModeSegmentedControlPreview() {
    PreviewSurface {
        ModeSegmentedControl(
            selectedMode = PlanningMode.AUTO,
            onSelectPlanningMode = {}
        )
    }
}
