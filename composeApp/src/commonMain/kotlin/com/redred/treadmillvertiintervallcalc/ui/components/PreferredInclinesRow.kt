package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redred.treadmillvertiintervallcalc.presentation.WorkoutInputField
import com.redred.treadmillvertiintervallcalc.presentation.WorkoutPlannerState
import org.jetbrains.compose.resources.stringResource
import vertirun.composeapp.generated.resources.Res
import vertirun.composeapp.generated.resources.field_preferred_hard_incline
import vertirun.composeapp.generated.resources.vr_ic_star

@Composable
internal fun PreferredInclinesRow(
    state: WorkoutPlannerState,
    onInputChanged: (WorkoutInputField, String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        VrIcon(
            resource = Res.drawable.vr_ic_star,
            contentDescription = null,
            size = 22.dp,
            tint = VertiGreen
        )
        Text(
            text = stringResource(Res.string.field_preferred_hard_incline),
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp),
            color = VertiText,
            style = MaterialTheme.typography.bodyMedium
        )
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            listOf("4", "5", "6", "7", "8").forEach { value ->
                val selected = state.preferredHardIncline == value
                Surface(
                    shape = VertiPillShape,
                    color = if (selected) VertiGreenSoft else Color(0xFFF5FAF7),
                    border = BorderStroke(1.dp, Color(0xFFD0DED6)),
                    modifier = Modifier.clickable { onInputChanged(WorkoutInputField.PREFERRED_HARD_INCLINE, value) }
                ) {
                    Text(
                        text = "$value %",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                        color = VertiGreen,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreferredInclinesRowPreview() {
    PreviewSurface {
        PreferredInclinesRow(
            state = WorkoutPlannerState(preferredHardIncline = "6"),
            onInputChanged = { _, _ -> }
        )
    }
}
