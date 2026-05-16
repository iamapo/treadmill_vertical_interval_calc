package com.redred.treadmillvertiintervallcalc.ui

import com.redred.treadmillvertiintervallcalc.ui.components.*

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redred.treadmillvertiintervallcalc.presentation.PlannerScreen
import com.redred.treadmillvertiintervallcalc.presentation.PlanningMode
import com.redred.treadmillvertiintervallcalc.presentation.WorkoutPlannerEvent
import com.redred.treadmillvertiintervallcalc.presentation.WorkoutPlannerViewModel

@Composable
fun WorkoutPlannerScreen(
    viewModel: WorkoutPlannerViewModel,
    modifier: Modifier = Modifier
) {
    val state = viewModel.state
    val clipboardManager = LocalClipboardManager.current

    LaunchedEffect(state.copyTextVersion) {
        if (state.copyTextVersion > 0 && state.copiedTextPreview.isNotBlank()) {
            clipboardManager.setText(AnnotatedString(state.copiedTextPreview))
        }
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = VertiPage
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(8.dp)
        ) {
            PlannerHeader(selectedScreen = state.selectedScreen)
            Column(modifier = Modifier.weight(1f)) {
                when (state.selectedScreen) {
                        PlannerScreen.INPUT -> WorkoutInputScreen(
                            state = state,
                            onManualSegmentDurationChanged = { segmentId, value ->
                                viewModel.onEvent(WorkoutPlannerEvent.ManualSegmentDurationChanged(segmentId, value))
                            },
                            onManualSegmentPaceChanged = { segmentId, value ->
                                viewModel.onEvent(WorkoutPlannerEvent.ManualSegmentPaceChanged(segmentId, value))
                            },
                            onManualSegmentInclineChanged = { segmentId, value ->
                                viewModel.onEvent(WorkoutPlannerEvent.ManualSegmentInclineChanged(segmentId, value))
                            },
                            onManualSegmentRepeatsChanged = { segmentId, value ->
                                viewModel.onEvent(WorkoutPlannerEvent.ManualSegmentRepeatsChanged(segmentId, value))
                            },
                            onManualSegmentRecoveryDurationChanged = { segmentId, value ->
                                viewModel.onEvent(WorkoutPlannerEvent.ManualSegmentRecoveryDurationChanged(segmentId, value))
                            },
                            onManualSegmentRecoveryPaceChanged = { segmentId, value ->
                                viewModel.onEvent(WorkoutPlannerEvent.ManualSegmentRecoveryPaceChanged(segmentId, value))
                            },
                            onManualSegmentRecoveryInclineChanged = { segmentId, value ->
                                viewModel.onEvent(WorkoutPlannerEvent.ManualSegmentRecoveryInclineChanged(segmentId, value))
                            },
                            onToggleAddManualSegmentTypePicker = {
                                viewModel.onEvent(WorkoutPlannerEvent.ToggleAddManualSegmentTypePicker)
                            },
                            onAddManualSegment = { type, duration, pace, incline, repeats, recoveryDuration, recoveryPace, recoveryIncline ->
                                viewModel.onEvent(
                                    WorkoutPlannerEvent.AddManualSegment(
                                        type = type,
                                        durationMinutes = duration,
                                        pace = pace,
                                        inclinePercent = incline,
                                        repeats = repeats,
                                        recoveryDurationMinutes = recoveryDuration,
                                        recoveryPace = recoveryPace,
                                        recoveryInclinePercent = recoveryIncline
                                    )
                                )
                            },
                            onRemoveManualSegment = {
                                viewModel.onEvent(WorkoutPlannerEvent.RemoveManualSegment(it))
                            },
                        onGenerateWorkout = {
                            viewModel.onEvent(WorkoutPlannerEvent.GenerateWorkout)
                        },
                        onClearSavedState = {
                            viewModel.onEvent(WorkoutPlannerEvent.ClearSavedState)
                        },
                        modifier = Modifier.fillMaxSize()
                    )

                        PlannerScreen.GENERATED -> {
                            val plan = state.generatedWorkoutPlan
                            if (plan == null) {
                                EmptyPlanMessage()
                            } else {
                                WorkoutGeneratedScreen(
                                    plan = plan,
                                    onOpenChecklist = {
                                        viewModel.onEvent(WorkoutPlannerEvent.SelectScreen(PlannerScreen.CHECKLIST))
                                    },
                                    onCopyWorkoutText = {
                                        viewModel.onEvent(WorkoutPlannerEvent.CopyWorkoutText)
                                    },
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }

                        PlannerScreen.CHECKLIST -> {
                            val plan = state.generatedWorkoutPlan
                            if (plan == null) {
                                EmptyPlanMessage()
                            } else {
                                WorkoutChecklistScreen(
                                    plan = plan,
                                    onToggleSegmentCompleted = {
                                        viewModel.onEvent(WorkoutPlannerEvent.ToggleSegmentCompleted(it))
                                    },
                                    onResetChecklist = {
                                        viewModel.onEvent(WorkoutPlannerEvent.ResetChecklist)
                                    },
                                    onCopyWorkoutText = {
                                        viewModel.onEvent(WorkoutPlannerEvent.CopyWorkoutText)
                                    },
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                }
            }
            PlannerBottomNavigation(
                selectedScreen = state.selectedScreen,
                hasPlan = state.generatedWorkoutPlan != null,
                onSelected = { viewModel.onEvent(WorkoutPlannerEvent.SelectScreen(it)) }
            )
        }
    }
}

@Preview
@Composable
private fun WorkoutPlannerScreenPreview() {
    val viewModel = remember { WorkoutPlannerViewModel() }
    PreviewSurface {
        WorkoutPlannerScreen(viewModel = viewModel)
    }
}

@Preview
@Composable
private fun WorkoutPlannerScreenManualEmptyPreview() {
    val viewModel = remember {
        WorkoutPlannerViewModel().apply {
            onEvent(WorkoutPlannerEvent.SelectPlanningMode(PlanningMode.MANUAL))
        }
    }
    PreviewSurface {
        WorkoutPlannerScreen(viewModel = viewModel)
    }
}
