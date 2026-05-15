package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import vertirun.composeapp.generated.resources.Res
import vertirun.composeapp.generated.resources.vr_ic_clock

@Composable
internal fun ManualMetric(
    icon: DrawableResource,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        VrIcon(resource = icon, contentDescription = null, size = 22.dp, tint = VertiGreen)
        Text(text = label, color = VertiMuted, style = MaterialTheme.typography.labelSmall, textAlign = TextAlign.Center)
        Text(text = value, color = VertiText, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
    }
}

@Preview
@Composable
private fun ManualMetricPreview() {
    PreviewSurface {
        ManualMetric(icon = Res.drawable.vr_ic_clock, label = "Gesamtdauer", value = "68 min")
    }
}
