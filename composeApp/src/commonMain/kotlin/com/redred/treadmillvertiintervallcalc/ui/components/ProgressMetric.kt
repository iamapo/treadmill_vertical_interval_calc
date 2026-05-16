package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.foundation.layout.Column
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
import org.jetbrains.compose.resources.DrawableResource
import verticaltreadmillrun.composeapp.generated.resources.Res
import verticaltreadmillrun.composeapp.generated.resources.vr_ic_check_circle

@Composable
internal fun ProgressMetric(
    icon: DrawableResource,
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    mutedIcon: Boolean = false
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        VrIcon(
            resource = icon,
            contentDescription = null,
            size = 26.dp,
            tint = if (mutedIcon) Color(0xFF8D949E) else VertiGreen
        )
        Text(
            text = label,
            color = VertiText,
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center
        )
        Text(text = value, color = VertiText, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
    }
}

@Preview
@Composable
private fun ProgressMetricPreview() {
    PreviewSurface {
        ProgressMetric(icon = Res.drawable.vr_ic_check_circle, label = "Erledigte Segmente", value = "3 / 7")
    }
}
