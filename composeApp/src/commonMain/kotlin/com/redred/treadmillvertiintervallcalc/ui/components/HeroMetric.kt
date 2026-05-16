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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import verticaltreadmillrun.composeapp.generated.resources.Res
import verticaltreadmillrun.composeapp.generated.resources.vr_ic_clock

@Composable
internal fun HeroMetric(
    icon: DrawableResource,
    label: String,
    value: String,
    unit: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        VrIcon(resource = icon, contentDescription = null, size = 34.dp, tint = VertiGreen)
        Text(
            text = label,
            color = VertiMuted,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(text = value, color = VertiText, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
        Text(text = unit, color = VertiMuted, style = MaterialTheme.typography.titleMedium)
    }
}

@Preview
@Composable
private fun HeroMetricPreview() {
    PreviewSurface {
        HeroMetric(icon = Res.drawable.vr_ic_clock, label = "Gesamtdauer", value = "75", unit = "min")
    }
}
