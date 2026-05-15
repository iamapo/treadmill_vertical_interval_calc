package com.redred.treadmillvertiintervallcalc.ui.components

import androidx.compose.foundation.background
import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redred.treadmillvertiintervallcalc.presentation.PlannerScreen
import org.jetbrains.compose.resources.stringResource
import vertirun.composeapp.generated.resources.Res
import vertirun.composeapp.generated.resources.tab_checklist
import vertirun.composeapp.generated.resources.tab_generated
import vertirun.composeapp.generated.resources.tab_input
import vertirun.composeapp.generated.resources.vr_ic_calendar_planer
import vertirun.composeapp.generated.resources.vr_ic_checklist
import vertirun.composeapp.generated.resources.vr_ic_clipboard

@Composable
internal fun PlannerBottomNavigation(
    selectedScreen: PlannerScreen,
    hasPlan: Boolean,
    onSelected: (PlannerScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Color.Transparent)
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 6.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp),
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            shadowElevation = 8.dp,
            tonalElevation = 0.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomNavItem(
                    label = stringResource(Res.string.tab_input),
                    icon = Res.drawable.vr_ic_calendar_planer,
                    selected = selectedScreen == PlannerScreen.INPUT,
                    enabled = true,
                    onClick = { onSelected(PlannerScreen.INPUT) }
                )
                BottomNavItem(
                    label = stringResource(Res.string.tab_generated),
                    icon = Res.drawable.vr_ic_clipboard,
                    selected = selectedScreen == PlannerScreen.GENERATED,
                    enabled = hasPlan,
                    onClick = { onSelected(PlannerScreen.GENERATED) }
                )
                BottomNavItem(
                    label = stringResource(Res.string.tab_checklist),
                    icon = Res.drawable.vr_ic_checklist,
                    selected = selectedScreen == PlannerScreen.CHECKLIST,
                    enabled = hasPlan,
                    onClick = { onSelected(PlannerScreen.CHECKLIST) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun PlannerBottomNavigationPreview() {
    PreviewSurface {
        PlannerBottomNavigation(
            selectedScreen = PlannerScreen.INPUT,
            hasPlan = true,
            onSelected = {}
        )
    }
}
