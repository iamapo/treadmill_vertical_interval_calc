package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redred.treadmillvertiintervallcalc.presentation.WorkoutInputField
import com.redred.treadmillvertiintervallcalc.presentation.WorkoutPlannerState
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import vertirun.composeapp.generated.resources.Res
import vertirun.composeapp.generated.resources.field_target_elevation
import vertirun.composeapp.generated.resources.vr_ic_mountain

@Composable
internal fun InputRow(
    icon: DrawableResource,
    label: StringResource,
    state: WorkoutPlannerState,
    field: WorkoutInputField,
    placeholder: String,
    onInputChanged: (WorkoutInputField, String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        VrIcon(resource = icon, contentDescription = null, size = 22.dp, tint = VertiGreen)
        Text(
            text = stringResource(label),
            modifier = Modifier
                .weight(1.35f)
                .padding(start = 10.dp),
            color = VertiText,
            style = MaterialTheme.typography.bodyMedium
        )
        PlannerTextField(
            state = state,
            field = field,
            placeholder = placeholder,
            keyboardType = keyboardTypeFor(field),
            onInputChanged = onInputChanged,
            modifier = Modifier.weight(1.1f)
        )
    }
}

@Preview
@Composable
private fun InputRowPreview() {
    PreviewSurface {
        Column {
            InputRow(
                icon = Res.drawable.vr_ic_mountain,
                label = Res.string.field_target_elevation,
                state = WorkoutPlannerState(targetElevation = "600"),
                field = WorkoutInputField.TARGET_ELEVATION,
                placeholder = "600 m",
                onInputChanged = { _, _ -> }
            )
        }
    }
}
