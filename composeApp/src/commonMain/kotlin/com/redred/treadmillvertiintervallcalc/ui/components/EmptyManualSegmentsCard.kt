package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vertirun.composeapp.generated.resources.Res
import vertirun.composeapp.generated.resources.vr_ic_flag

@Composable
internal fun EmptyManualSegmentsCard() {
    VertiCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 34.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            VrIcon(
                resource = Res.drawable.vr_ic_flag,
                contentDescription = null,
                size = 64.dp,
                tint = Color(0xFFA9C3B5)
            )
            Text(
                text = "Noch keine Segmente",
                color = VertiText,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Füge dein erstes Segment hinzu,\num dein Workout manuell aufzubauen.",
                modifier = Modifier.padding(top = 10.dp),
                color = VertiMuted,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun EmptyManualSegmentsCardPreview() {
    PreviewSurface {
        EmptyManualSegmentsCard()
    }
}
