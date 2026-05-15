package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redred.treadmillvertiintervallcalc.presentation.PlannerScreen
import org.jetbrains.compose.resources.stringResource
import vertirun.composeapp.generated.resources.Res
import vertirun.composeapp.generated.resources.app_title
import vertirun.composeapp.generated.resources.tab_checklist
import vertirun.composeapp.generated.resources.tab_generated
import vertirun.composeapp.generated.resources.tab_input

@Composable
internal fun PlannerHeader(selectedScreen: PlannerScreen) {
    when (selectedScreen) {
        PlannerScreen.INPUT -> stringResource(Res.string.tab_input)
        PlannerScreen.GENERATED -> stringResource(Res.string.tab_generated)
        PlannerScreen.CHECKLIST -> stringResource(Res.string.tab_checklist)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MountainLogo()
        Spacer(Modifier.width(10.dp))
        Text(
            text = stringResource(Res.string.app_title),
            color = VertiText,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
private fun PlannerHeaderPreview() {
    PreviewSurface {
        PlannerHeader(selectedScreen = PlannerScreen.INPUT)
    }
}
