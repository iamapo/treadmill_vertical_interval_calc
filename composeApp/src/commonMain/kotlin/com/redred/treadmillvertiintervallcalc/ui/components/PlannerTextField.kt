package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redred.treadmillvertiintervallcalc.presentation.WorkoutInputField
import com.redred.treadmillvertiintervallcalc.presentation.WorkoutPlannerState

@Composable
internal fun PlannerTextField(
    state: WorkoutPlannerState,
    field: WorkoutInputField,
    placeholder: String,
    keyboardType: KeyboardType,
    onInputChanged: (WorkoutInputField, String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = state.valueFor(field),
        onValueChange = { onInputChanged(field, it) },
        placeholder = { Text(placeholder) },
        isError = state.validationErrors[field] != null,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = modifier.height(50.dp)
    )
}

internal fun keyboardTypeFor(field: WorkoutInputField): KeyboardType =
    when (field) {
        WorkoutInputField.HARD_INTERVAL_PACE,
        WorkoutInputField.RECOVERY_PACE,
        WorkoutInputField.WARM_UP_PACE,
        WorkoutInputField.COOL_DOWN_PACE -> KeyboardType.Text
        else -> KeyboardType.Decimal
    }

@Preview
@Composable
private fun PlannerTextFieldPreview() {
    PreviewSurface {
        PlannerTextField(
            state = WorkoutPlannerState(targetElevation = "600"),
            field = WorkoutInputField.TARGET_ELEVATION,
            placeholder = "600 m",
            keyboardType = KeyboardType.Decimal,
            onInputChanged = { _, _ -> }
        )
    }
}
