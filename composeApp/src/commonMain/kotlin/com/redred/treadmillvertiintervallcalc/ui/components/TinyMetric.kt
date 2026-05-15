package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import vertirun.composeapp.generated.resources.Res
import vertirun.composeapp.generated.resources.vr_ic_clock

@Composable
internal fun TinyMetric(
    label: String,
    value: String,
    icon: DrawableResource,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        VrIcon(resource = icon, contentDescription = null, size = 20.dp, tint = VertiGreen)
        Text(
            text = label,
            modifier = Modifier.padding(start = 6.dp),
            color = VertiMuted,
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = value,
            modifier = Modifier.padding(start = 8.dp),
            color = VertiText,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
private fun TinyMetricPreview() {
    PreviewSurface {
        TinyMetric(label = "Dauer", value = "10 min", icon = Res.drawable.vr_ic_clock)
    }
}
