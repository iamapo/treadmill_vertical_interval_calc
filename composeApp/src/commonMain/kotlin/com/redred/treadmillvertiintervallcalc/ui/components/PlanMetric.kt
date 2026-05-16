package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import verticaltreadmillrun.composeapp.generated.resources.Res
import verticaltreadmillrun.composeapp.generated.resources.vr_ic_clock

@Composable
internal fun PlanMetric(icon: DrawableResource, value: String) {
    Column(
        modifier = Modifier.padding(horizontal = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VrIcon(resource = icon, contentDescription = null, size = 20.dp, tint = VertiGreen)
        Text(
            text = value,
            color = VertiText,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
private fun PlanMetricPreview() {
    PreviewSurface {
        PlanMetric(icon = Res.drawable.vr_ic_clock, value = "10 min")
    }
}
