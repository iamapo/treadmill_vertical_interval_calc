package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import verticaltreadmillrun.composeapp.generated.resources.Res
import verticaltreadmillrun.composeapp.generated.resources.field_cooldown_incline
import verticaltreadmillrun.composeapp.generated.resources.field_preferred_hard_incline
import verticaltreadmillrun.composeapp.generated.resources.field_preferred_recovery_incline
import verticaltreadmillrun.composeapp.generated.resources.field_warmup_incline
import verticaltreadmillrun.composeapp.generated.resources.vr_ic_incline_up

@Composable
internal fun PreferredInclinesRow(
    state: WorkoutPlannerState,
    onInputChanged: (WorkoutInputField, String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        InclinePillsRow(
            label = stringResource(Res.string.field_warmup_incline),
            selectedValue = state.warmUpIncline,
            onSelect = { onInputChanged(WorkoutInputField.WARM_UP_INCLINE, it) }
        )
        InclinePillsRow(
            label = stringResource(Res.string.field_preferred_recovery_incline),
            selectedValue = state.recoveryIncline,
            onSelect = { onInputChanged(WorkoutInputField.RECOVERY_INCLINE, it) }
        )
        InclinePillsRow(
            label = stringResource(Res.string.field_preferred_hard_incline),
            selectedValue = state.preferredHardIncline,
            onSelect = { onInputChanged(WorkoutInputField.PREFERRED_HARD_INCLINE, it) }
        )
        InclinePillsRow(
            label = stringResource(Res.string.field_cooldown_incline),
            selectedValue = state.coolDownIncline,
            onSelect = { onInputChanged(WorkoutInputField.COOL_DOWN_INCLINE, it) }
        )
    }
}

@Composable
private fun InclinePillsRow(
    label: String,
    selectedValue: String,
    onSelect: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        VrIcon(
            resource = Res.drawable.vr_ic_incline_up,
            contentDescription = null,
            size = 22.dp,
            tint = VertiGreen
        )
        Text(
            text = label,
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp),
            color = VertiText,
            style = MaterialTheme.typography.bodyMedium
        )
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            listOf("0", "2", "4", "6", "8", "10").forEach { value ->
                val selected = selectedValue == value
                Surface(
                    shape = VertiPillShape,
                    color = if (selected) VertiGreenSoft else Color(0xFFF5FAF7),
                    border = BorderStroke(1.dp, Color(0xFFD0DED6)),
                    modifier = Modifier.clickable { onSelect(value) }
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
            state = WorkoutPlannerState(preferredHardIncline = "6", recoveryIncline = "4", warmUpIncline = "2", coolDownIncline = "0"),
            onInputChanged = { _, _ -> }
        )
    }
}
