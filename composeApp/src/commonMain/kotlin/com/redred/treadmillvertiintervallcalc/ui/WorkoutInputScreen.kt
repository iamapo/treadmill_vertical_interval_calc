package com.redred.treadmillvertiintervallcalc.ui

import com.redred.treadmillvertiintervallcalc.ui.components.*

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redred.treadmillvertiintervallcalc.presentation.ManualSegmentInput
import com.redred.treadmillvertiintervallcalc.presentation.ManualSegmentType
import com.redred.treadmillvertiintervallcalc.presentation.PlanningMode
import com.redred.treadmillvertiintervallcalc.presentation.WorkoutInputField
import com.redred.treadmillvertiintervallcalc.presentation.WorkoutPlannerState
import org.jetbrains.compose.resources.stringResource
import vertirun.composeapp.generated.resources.Res
import vertirun.composeapp.generated.resources.button_generate_workout
import vertirun.composeapp.generated.resources.button_clear_saved_state
import vertirun.composeapp.generated.resources.field_cooldown_duration
import vertirun.composeapp.generated.resources.field_incline_step
import vertirun.composeapp.generated.resources.field_max_incline
import vertirun.composeapp.generated.resources.field_target_elevation
import vertirun.composeapp.generated.resources.field_total_duration
import vertirun.composeapp.generated.resources.field_warmup_duration
import vertirun.composeapp.generated.resources.section_inclines
import vertirun.composeapp.generated.resources.section_intervals_paces
import vertirun.composeapp.generated.resources.section_workout_target
import vertirun.composeapp.generated.resources.vr_ic_clock
import vertirun.composeapp.generated.resources.vr_ic_flame
import vertirun.composeapp.generated.resources.vr_ic_heart_recovery
import vertirun.composeapp.generated.resources.vr_ic_incline_up
import vertirun.composeapp.generated.resources.vr_ic_interval_bars
import vertirun.composeapp.generated.resources.vr_ic_mountain
import vertirun.composeapp.generated.resources.vr_ic_snowflake
import vertirun.composeapp.generated.resources.vr_ic_spark
import vertirun.composeapp.generated.resources.vr_ic_step_dotted
import vertirun.composeapp.generated.resources.vr_ic_target

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutInputScreen(
    state: WorkoutPlannerState,
    onInputChanged: (WorkoutInputField, String) -> Unit,
    onSelectPlanningMode: (PlanningMode) -> Unit,
    onManualSegmentDurationChanged: (String, String) -> Unit,
    onManualSegmentPaceChanged: (String, String) -> Unit,
    onManualSegmentInclineChanged: (String, String) -> Unit,
    onManualSegmentRepeatsChanged: (String, String) -> Unit,
    onManualSegmentRecoveryDurationChanged: (String, String) -> Unit,
    onManualSegmentRecoveryPaceChanged: (String, String) -> Unit,
    onManualSegmentRecoveryInclineChanged: (String, String) -> Unit,
    onManualSegmentTypeChanged: (String, ManualSegmentType) -> Unit,
    onToggleAddManualSegmentTypePicker: () -> Unit,
    onAddManualSegment: (ManualSegmentType, String, String, String, String, String, String, String) -> Unit,
    onRemoveManualSegment: (String) -> Unit,
    onGenerateWorkout: () -> Unit,
    onClearSavedState: () -> Unit,
    modifier: Modifier = Modifier
) {
    val addSegmentSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            ModeSegmentedControl(
                selectedMode = state.planningMode,
                onSelectPlanningMode = onSelectPlanningMode
            )
        }

        if (state.planningMode == PlanningMode.MANUAL) {
            item { ManualSummaryCard(state) }
            item { AddSegmentButton(onClick = onToggleAddManualSegmentTypePicker) }
            if (state.manualSegments.isEmpty()) {
                item { EmptyManualSegmentsCard() }
            } else {
                state.manualSegments.forEach { segment ->
                    item {
                        ManualSegmentCard(
                            segment = segment,
                            onDurationChanged = { onManualSegmentDurationChanged(segment.id, it) },
                            onPaceChanged = { onManualSegmentPaceChanged(segment.id, it) },
                            onInclineChanged = { onManualSegmentInclineChanged(segment.id, it) },
                            onRepeatsChanged = { onManualSegmentRepeatsChanged(segment.id, it) },
                            onRecoveryDurationChanged = { onManualSegmentRecoveryDurationChanged(segment.id, it) },
                            onRecoveryPaceChanged = { onManualSegmentRecoveryPaceChanged(segment.id, it) },
                            onRecoveryInclineChanged = { onManualSegmentRecoveryInclineChanged(segment.id, it) },
                            onRemove = { onRemoveManualSegment(segment.id) }
                        )
                    }
                }
            }
        } else {
            item {
                AutoSectionCard(number = 1, title = Res.string.section_workout_target, icon = Res.drawable.vr_ic_target) {
                    InputRow(Res.drawable.vr_ic_mountain, Res.string.field_target_elevation, state, WorkoutInputField.TARGET_ELEVATION, "600 m", onInputChanged)
                    InputRow(Res.drawable.vr_ic_clock, Res.string.field_total_duration, state, WorkoutInputField.TOTAL_DURATION, "75 min", onInputChanged)
                    InputRow(Res.drawable.vr_ic_flame, Res.string.field_warmup_duration, state, WorkoutInputField.WARM_UP_DURATION, "10 min", onInputChanged)
                    InputRow(Res.drawable.vr_ic_snowflake, Res.string.field_cooldown_duration, state, WorkoutInputField.COOL_DOWN_DURATION, "10 min", onInputChanged)
                }
            }
            item {
                AutoSectionCard(number = 2, title = Res.string.section_inclines, icon = Res.drawable.vr_ic_incline_up) {
                    InputRow(Res.drawable.vr_ic_incline_up, Res.string.field_max_incline, state, WorkoutInputField.MAX_INCLINE, "6 %", onInputChanged)
                    InputRow(Res.drawable.vr_ic_step_dotted, Res.string.field_incline_step, state, WorkoutInputField.INCLINE_STEP, "1 %", onInputChanged)
                    PreferredInclinesRow(state, onInputChanged)
                }
            }
            item {
                AutoSectionCard(number = 3, title = Res.string.section_intervals_paces, icon = Res.drawable.vr_ic_clock) {
                    DualInputRow(Res.drawable.vr_ic_interval_bars, "Hart Dauer", state, WorkoutInputField.HARD_INTERVAL_DURATION, WorkoutInputField.HARD_INTERVAL_PACE, onInputChanged)
                    DualInputRow(Res.drawable.vr_ic_heart_recovery, "Erholung Dauer", state, WorkoutInputField.RECOVERY_INTERVAL_DURATION, WorkoutInputField.RECOVERY_PACE, onInputChanged)
                    DualInputRow(Res.drawable.vr_ic_flame, "Warm-up", state, WorkoutInputField.WARM_UP_DURATION, WorkoutInputField.WARM_UP_PACE, onInputChanged)
                    DualInputRow(Res.drawable.vr_ic_snowflake, "Cool-down", state, WorkoutInputField.COOL_DOWN_DURATION, WorkoutInputField.COOL_DOWN_PACE, onInputChanged)
                }
            }
        }

        item {
            Button(
                onClick = onGenerateWorkout,
                enabled = state.planningMode == PlanningMode.AUTO || state.manualSegments.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = VertiButtonShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = VertiGreen,
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFFDCE8E1),
                    disabledContentColor = Color(0xFF8F9B96)
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    VrIcon(
                        resource = Res.drawable.vr_ic_spark,
                        contentDescription = null,
                        tint = Color.White,
                        size = 24.dp
                    )
                    Text(
                        text = stringResource(Res.string.button_generate_workout),
                        modifier = Modifier.padding(start = 10.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        item {
            OutlinedButton(
                onClick = onClearSavedState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = VertiButtonShape
            ) {
                Text(text = stringResource(Res.string.button_clear_saved_state))
            }
        }
    }

    if (state.showAddSegmentTypePicker) {
        ModalBottomSheet(
            sheetState = addSegmentSheetState,
            onDismissRequest = onToggleAddManualSegmentTypePicker
        ) {
            AddSegmentSheet(
                modifier = Modifier.fillMaxHeight(),
                onAddManualSegment = onAddManualSegment,
                onDismiss = onToggleAddManualSegmentTypePicker
            )
        }
    }

    @Suppress("UNUSED_EXPRESSION")
    onManualSegmentTypeChanged
}

@Preview
@Composable
private fun WorkoutInputScreenPreview() {
    PreviewSurface {
        WorkoutInputScreen(
            state = WorkoutPlannerState(
                targetElevation = "600",
                totalDuration = "75",
                maxIncline = "6",
                inclineStep = "1",
                hardIntervalPace = "5:10",
                recoveryPace = "6:00"
            ),
            onInputChanged = { _, _ -> },
            onSelectPlanningMode = {},
            onManualSegmentDurationChanged = { _, _ -> },
            onManualSegmentPaceChanged = { _, _ -> },
            onManualSegmentInclineChanged = { _, _ -> },
            onManualSegmentRepeatsChanged = { _, _ -> },
            onManualSegmentRecoveryDurationChanged = { _, _ -> },
            onManualSegmentRecoveryPaceChanged = { _, _ -> },
            onManualSegmentRecoveryInclineChanged = { _, _ -> },
            onManualSegmentTypeChanged = { _, _ -> },
            onToggleAddManualSegmentTypePicker = {},
            onAddManualSegment = { _, _, _, _, _, _, _, _ -> },
            onRemoveManualSegment = {},
            onGenerateWorkout = {},
            onClearSavedState = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
private fun WorkoutInputScreenManualPreview() {
    PreviewSurface {
        WorkoutInputScreen(
            state = WorkoutPlannerState(
                planningMode = PlanningMode.MANUAL,
                manualSegments = listOf(
                    ManualSegmentInput(
                        id = "preview_manual_1",
                        type = ManualSegmentType.REPETITION,
                        durationMinutes = "5",
                        pace = "5:05",
                        inclinePercent = "7",
                        repeats = "4",
                        recoveryDurationMinutes = "3",
                        recoveryPace = "6:05",
                        recoveryInclinePercent = "3"
                    )
                ),
                manualTotalDurationMinutes = 68,
                manualTotalDistanceKilometers = 11.2,
                manualAveragePaceMinutesPerKm = 5.75,
                manualElevationMeters = 540.0
            ),
            onInputChanged = { _, _ -> },
            onSelectPlanningMode = {},
            onManualSegmentDurationChanged = { _, _ -> },
            onManualSegmentPaceChanged = { _, _ -> },
            onManualSegmentInclineChanged = { _, _ -> },
            onManualSegmentRepeatsChanged = { _, _ -> },
            onManualSegmentRecoveryDurationChanged = { _, _ -> },
            onManualSegmentRecoveryPaceChanged = { _, _ -> },
            onManualSegmentRecoveryInclineChanged = { _, _ -> },
            onManualSegmentTypeChanged = { _, _ -> },
            onToggleAddManualSegmentTypePicker = {},
            onAddManualSegment = { _, _, _, _, _, _, _, _ -> },
            onRemoveManualSegment = {},
            onGenerateWorkout = {},
            onClearSavedState = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}
