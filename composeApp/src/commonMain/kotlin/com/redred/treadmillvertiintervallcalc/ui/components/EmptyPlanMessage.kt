package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import vertirun.composeapp.generated.resources.Res
import vertirun.composeapp.generated.resources.empty_plan_message

@Composable
internal fun EmptyPlanMessage() {
    Text(
        text = stringResource(Res.string.empty_plan_message),
        modifier = Modifier.padding(20.dp),
        style = MaterialTheme.typography.bodyLarge
    )
}

@Preview
@Composable
private fun EmptyPlanMessagePreview() {
    PreviewSurface {
        EmptyPlanMessage()
    }
}
