package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import vertirun.composeapp.generated.resources.Res
import vertirun.composeapp.generated.resources.vr_ic_clock

@Composable
internal fun SheetField(
    icon: DrawableResource,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        VrIcon(resource = icon, contentDescription = null, size = 24.dp, tint = VertiGreen)
        Text(
            text = label,
            modifier = Modifier
                .weight(1f)
                .padding(start = 14.dp),
            color = VertiText,
            style = MaterialTheme.typography.bodyLarge
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            modifier = Modifier
                .weight(1.5f)
                .height(58.dp)
        )
    }
}

@Preview
@Composable
private fun SheetFieldPreview() {
    PreviewSurface {
        SheetField(
            icon = Res.drawable.vr_ic_clock,
            label = "Dauer",
            value = "5 min",
            onValueChange = {},
            keyboardType = KeyboardType.Number
        )
    }
}
