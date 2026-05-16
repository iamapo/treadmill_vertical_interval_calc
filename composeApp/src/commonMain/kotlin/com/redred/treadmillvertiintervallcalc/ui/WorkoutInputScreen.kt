package com.redred.treadmillvertiintervallcalc.ui

import com.redred.treadmillvertiintervallcalc.ui.components.*

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
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
import com.redred.treadmillvertiintervallcalc.presentation.WorkoutPlannerState
import org.jetbrains.compose.resources.stringResource
import verticaltreadmillrun.composeapp.generated.resources.Res
import verticaltreadmillrun.composeapp.generated.resources.button_generate_workout
import verticaltreadmillrun.composeapp.generated.resources.button_clear_saved_state
import verticaltreadmillrun.composeapp.generated.resources.vr_ic_spark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutInputScreen(
    state: WorkoutPlannerState,
    onManualSegmentDurationChanged: (String, String) -> Unit,
    onManualSegmentPaceChanged: (String, String) -> Unit,
    onManualSegmentInclineChanged: (String, String) -> Unit,
    onManualSegmentRepeatsChanged: (String, String) -> Unit,
    onManualSegmentRecoveryDurationChanged: (String, String) -> Unit,
    onManualSegmentRecoveryPaceChanged: (String, String) -> Unit,
    onManualSegmentRecoveryInclineChanged: (String, String) -> Unit,
    onToggleAddManualSegmentTypePicker: () -> Unit,
    onAddManualSegment: (ManualSegmentType, String, String, String, String, String, String, String) -> Unit,
    onRemoveManualSegment: (String) -> Unit,
    onGenerateWorkout: () -> Unit,
    onClearSavedState: () -> Unit,
    modifier: Modifier = Modifier
) {
    val addSegmentSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LazyColumn(
        modifier = modifier
            .imePadding()
            .navigationBarsPadding(),
        contentPadding = PaddingValues(start = 10.dp, top = 2.dp, end = 10.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
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

        item {
            Button(
                onClick = onGenerateWorkout,
                enabled = state.manualSegments.isNotEmpty(),
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
            onManualSegmentDurationChanged = { _, _ -> },
            onManualSegmentPaceChanged = { _, _ -> },
            onManualSegmentInclineChanged = { _, _ -> },
            onManualSegmentRepeatsChanged = { _, _ -> },
            onManualSegmentRecoveryDurationChanged = { _, _ -> },
            onManualSegmentRecoveryPaceChanged = { _, _ -> },
            onManualSegmentRecoveryInclineChanged = { _, _ -> },
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
            onManualSegmentDurationChanged = { _, _ -> },
            onManualSegmentPaceChanged = { _, _ -> },
            onManualSegmentInclineChanged = { _, _ -> },
            onManualSegmentRepeatsChanged = { _, _ -> },
            onManualSegmentRecoveryDurationChanged = { _, _ -> },
            onManualSegmentRecoveryPaceChanged = { _, _ -> },
            onManualSegmentRecoveryInclineChanged = { _, _ -> },
            onToggleAddManualSegmentTypePicker = {},
            onAddManualSegment = { _, _, _, _, _, _, _, _ -> },
            onRemoveManualSegment = {},
            onGenerateWorkout = {},
            onClearSavedState = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}
