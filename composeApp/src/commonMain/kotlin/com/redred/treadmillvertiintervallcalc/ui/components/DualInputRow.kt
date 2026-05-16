package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redred.treadmillvertiintervallcalc.presentation.WorkoutInputField
import com.redred.treadmillvertiintervallcalc.presentation.WorkoutPlannerState
import org.jetbrains.compose.resources.DrawableResource
import verticaltreadmillrun.composeapp.generated.resources.Res
import verticaltreadmillrun.composeapp.generated.resources.vr_ic_interval_bars

@Composable
internal fun DualInputRow(
    icon: DrawableResource,
    label: String,
    state: WorkoutPlannerState,
    durationField: WorkoutInputField,
    paceField: WorkoutInputField,
    onInputChanged: (WorkoutInputField, String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        VrIcon(resource = icon, contentDescription = null, size = 22.dp, tint = VertiGreen)
        Text(
            text = label,
            modifier = Modifier.weight(1.2f),
            color = VertiText,
            style = MaterialTheme.typography.bodyMedium
        )
        PlannerTextField(state, durationField, "5 min", KeyboardType.Decimal, onInputChanged, Modifier.weight(0.95f))
        PlannerTextField(state, paceField, "5:10 min/km", KeyboardType.Text, onInputChanged, Modifier.weight(1.1f))
    }
}

@Preview
@Composable
private fun DualInputRowPreview() {
    PreviewSurface {
        Column {
            DualInputRow(
                icon = Res.drawable.vr_ic_interval_bars,
                label = "Hart Dauer",
                state = WorkoutPlannerState(hardIntervalDuration = "5", hardIntervalPace = "5:10"),
                durationField = WorkoutInputField.HARD_INTERVAL_DURATION,
                paceField = WorkoutInputField.HARD_INTERVAL_PACE,
                onInputChanged = { _, _ -> }
            )
        }
    }
}
