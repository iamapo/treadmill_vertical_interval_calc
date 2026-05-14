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
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import vertirun.composeapp.generated.resources.*

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
                    text = stringResource(Res.string.app_title),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = stringResource(Res.string.app_description),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        item {
            FieldSection(title = Res.string.section_workout_target) {
                NumberField(state, WorkoutInputField.TARGET_ELEVATION, Res.string.field_target_elevation, "1200", onInputChanged)
                NumberField(state, WorkoutInputField.TOTAL_DURATION, Res.string.field_total_duration, "120", onInputChanged)
                NumberField(state, WorkoutInputField.WARM_UP_DURATION, Res.string.field_warmup_duration, "10", onInputChanged)
                NumberField(state, WorkoutInputField.COOL_DOWN_DURATION, Res.string.field_cooldown_duration, "10", onInputChanged)
            }
        }

        item {
            FieldSection(title = Res.string.section_inclines) {
                NumberField(state, WorkoutInputField.MAX_INCLINE, Res.string.field_max_incline, "15", onInputChanged)
                NumberField(state, WorkoutInputField.INCLINE_STEP, Res.string.field_incline_step, "0.5", onInputChanged)
                NumberField(state, WorkoutInputField.PREFERRED_HARD_INCLINE, Res.string.field_preferred_hard_incline, "15", onInputChanged)
                NumberField(state, WorkoutInputField.PREFERRED_RECOVERY_INCLINE, Res.string.field_preferred_recovery_incline, "5", onInputChanged)
            }
        }

        item {
            FieldSection(title = Res.string.section_intervals_paces) {
                NumberField(state, WorkoutInputField.HARD_INTERVAL_DURATION, Res.string.field_hard_interval_duration, "5", onInputChanged)
                NumberField(state, WorkoutInputField.RECOVERY_INTERVAL_DURATION, Res.string.field_recovery_interval_duration, "3", onInputChanged)
                PaceField(state, WorkoutInputField.HARD_INTERVAL_PACE, Res.string.field_hard_interval_pace, "8:30", onInputChanged)
                PaceField(state, WorkoutInputField.RECOVERY_PACE, Res.string.field_recovery_pace, "9:30", onInputChanged)
                PaceField(state, WorkoutInputField.WARM_UP_PACE, Res.string.field_warmup_pace, "7:30", onInputChanged)
                PaceField(state, WorkoutInputField.COOL_DOWN_PACE, Res.string.field_cooldown_pace, "8:30", onInputChanged)
            }
        }

        item {
            Button(
                onClick = onGenerateWorkout,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(Res.string.button_generate_workout), textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
private fun FieldSection(
    title: StringResource,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = stringResource(title),
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
    label: StringResource,
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
    label: StringResource,
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
    label: StringResource,
    placeholder: String,
    keyboardType: KeyboardType,
    onInputChanged: (WorkoutInputField, String) -> Unit
) {
    val error = state.validationErrors[field]
    OutlinedTextField(
        value = state.valueFor(field),
        onValueChange = { onInputChanged(field, it) },
        label = { Text(stringResource(label)) },
        placeholder = { Text(placeholder) },
        isError = error != null,
        supportingText = {
            Text(error ?: when (field) {
                WorkoutInputField.HARD_INTERVAL_PACE,
                WorkoutInputField.RECOVERY_PACE,
                WorkoutInputField.WARM_UP_PACE,
                WorkoutInputField.COOL_DOWN_PACE -> stringResource(Res.string.support_pace_format)
                WorkoutInputField.TARGET_ELEVATION -> stringResource(Res.string.support_meters)
                WorkoutInputField.MAX_INCLINE,
                WorkoutInputField.INCLINE_STEP,
                WorkoutInputField.PREFERRED_HARD_INCLINE,
                WorkoutInputField.PREFERRED_RECOVERY_INCLINE -> stringResource(Res.string.support_percent)
                else -> stringResource(Res.string.support_minutes)
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
