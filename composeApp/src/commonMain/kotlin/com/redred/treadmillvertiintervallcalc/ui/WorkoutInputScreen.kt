package com.redred.treadmillvertiintervallcalc.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redred.treadmillvertiintervallcalc.presentation.WorkoutInputField
import com.redred.treadmillvertiintervallcalc.presentation.WorkoutPlannerState

@Composable
fun WorkoutInputScreen(
    state: WorkoutPlannerState,
    onInputChanged: (WorkoutInputField, String) -> Unit,
    onGenerateWorkout: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "VertiRun",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Plan a treadmill elevation interval workout from duration, target climb, paces, and incline limits.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        item {
            FieldSection(title = "Workout target") {
                NumberField(state, WorkoutInputField.TARGET_ELEVATION, "Target elevation gain", "1200", onInputChanged)
                NumberField(state, WorkoutInputField.TOTAL_DURATION, "Total duration", "120", onInputChanged)
                NumberField(state, WorkoutInputField.WARM_UP_DURATION, "Warm-up duration", "10", onInputChanged)
                NumberField(state, WorkoutInputField.COOL_DOWN_DURATION, "Cool-down duration", "10", onInputChanged)
            }
        }

        item {
            FieldSection(title = "Inclines") {
                NumberField(state, WorkoutInputField.MAX_INCLINE, "Maximum incline", "15", onInputChanged)
                NumberField(state, WorkoutInputField.INCLINE_STEP, "Incline step size", "0.5", onInputChanged)
                NumberField(state, WorkoutInputField.PREFERRED_HARD_INCLINE, "Preferred hard interval incline", "15", onInputChanged)
                NumberField(state, WorkoutInputField.PREFERRED_RECOVERY_INCLINE, "Preferred recovery incline", "5", onInputChanged)
            }
        }

        item {
            FieldSection(title = "Intervals and paces") {
                NumberField(state, WorkoutInputField.HARD_INTERVAL_DURATION, "Hard interval duration", "5", onInputChanged)
                NumberField(state, WorkoutInputField.RECOVERY_INTERVAL_DURATION, "Recovery interval duration", "3", onInputChanged)
                PaceField(state, WorkoutInputField.HARD_INTERVAL_PACE, "Hard interval pace", "8:30", onInputChanged)
                PaceField(state, WorkoutInputField.RECOVERY_PACE, "Recovery pace", "9:30", onInputChanged)
                PaceField(state, WorkoutInputField.WARM_UP_PACE, "Warm-up pace", "7:30", onInputChanged)
                PaceField(state, WorkoutInputField.COOL_DOWN_PACE, "Cool-down pace", "8:30", onInputChanged)
            }
        }

        item {
            Button(
                onClick = onGenerateWorkout,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Generate workout", textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
private fun FieldSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Column(verticalArrangement = Arrangement.spacedBy(10.dp), content = content)
    }
}

@Composable
private fun NumberField(
    state: WorkoutPlannerState,
    field: WorkoutInputField,
    label: String,
    placeholder: String,
    onInputChanged: (WorkoutInputField, String) -> Unit
) {
    PlannerTextField(
        state = state,
        field = field,
        label = label,
        placeholder = placeholder,
        keyboardType = KeyboardType.Decimal,
        onInputChanged = onInputChanged
    )
}

@Composable
private fun PaceField(
    state: WorkoutPlannerState,
    field: WorkoutInputField,
    label: String,
    placeholder: String,
    onInputChanged: (WorkoutInputField, String) -> Unit
) {
    PlannerTextField(
        state = state,
        field = field,
        label = label,
        placeholder = placeholder,
        keyboardType = KeyboardType.Text,
        onInputChanged = onInputChanged
    )
}

@Composable
private fun PlannerTextField(
    state: WorkoutPlannerState,
    field: WorkoutInputField,
    label: String,
    placeholder: String,
    keyboardType: KeyboardType,
    onInputChanged: (WorkoutInputField, String) -> Unit
) {
    val error = state.validationErrors[field]
    OutlinedTextField(
        value = state.valueFor(field),
        onValueChange = { onInputChanged(field, it) },
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        isError = error != null,
        supportingText = {
            Text(error ?: when (field) {
                WorkoutInputField.HARD_INTERVAL_PACE,
                WorkoutInputField.RECOVERY_PACE,
                WorkoutInputField.WARM_UP_PACE,
                WorkoutInputField.COOL_DOWN_PACE -> "Format: mm:ss"
                WorkoutInputField.TARGET_ELEVATION -> "Meters"
                WorkoutInputField.MAX_INCLINE,
                WorkoutInputField.INCLINE_STEP,
                WorkoutInputField.PREFERRED_HARD_INCLINE,
                WorkoutInputField.PREFERRED_RECOVERY_INCLINE -> "Percent"
                else -> "Minutes"
            })
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
private fun WorkoutInputScreenPreview() {
    PreviewSurface {
        WorkoutInputScreen(
            state = WorkoutPlannerState(),
            onInputChanged = { _, _ -> },
            onGenerateWorkout = {}
        )
    }
}
