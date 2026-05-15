package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun VertiCard(
    modifier: Modifier = Modifier,
    borderColor: Color = VertiLine,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = VertiCardShape,
        color = Color.White,
        tonalElevation = 0.dp,
        shadowElevation = 3.dp,
        border = BorderStroke(1.dp, borderColor),
        content = content
    )
}

@Preview
@Composable
private fun VertiCardPreview() {
    PreviewSurface {
        VertiCard {
            Text("VertiCard")
        }
    }
}
