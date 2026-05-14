package com.redred.treadmillvertiintervallcalc.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redred.treadmillvertiintervallcalc.presentation.PlannerScreen
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
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding()
        ) {
            PlannerTabs(
                selectedScreen = state.selectedScreen,
                hasPlan = state.generatedWorkoutPlan != null,
                onSelected = { viewModel.onEvent(WorkoutPlannerEvent.SelectScreen(it)) }
            )

            when (state.selectedScreen) {
                PlannerScreen.INPUT -> WorkoutInputScreen(
                    state = state,
                    onInputChanged = { field, value ->
                        viewModel.onEvent(WorkoutPlannerEvent.InputChanged(field, value))
                    },
                    onGenerateWorkout = {
                        viewModel.onEvent(WorkoutPlannerEvent.GenerateWorkout)
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
    }
}

@Composable
private fun PlannerTabs(
    selectedScreen: PlannerScreen,
    hasPlan: Boolean,
    onSelected: (PlannerScreen) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        PlannerTab(
            label = "Input",
            selected = selectedScreen == PlannerScreen.INPUT,
            enabled = true,
            onClick = { onSelected(PlannerScreen.INPUT) },
            modifier = Modifier.weight(1f)
        )
        PlannerTab(
            label = "Generated",
            selected = selectedScreen == PlannerScreen.GENERATED,
            enabled = hasPlan,
            onClick = { onSelected(PlannerScreen.GENERATED) },
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        )
        PlannerTab(
            label = "Checklist",
            selected = selectedScreen == PlannerScreen.CHECKLIST,
            enabled = hasPlan,
            onClick = { onSelected(PlannerScreen.CHECKLIST) },
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        )
    }
}

@Composable
private fun PlannerTab(
    label: String,
    selected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = selected,
        enabled = enabled,
        onClick = onClick,
        label = { Text(label) },
        modifier = modifier
    )
}

@Composable
private fun EmptyPlanMessage() {
    Text(
        text = "Generate a workout first.",
        modifier = Modifier.padding(20.dp),
        style = MaterialTheme.typography.bodyLarge
    )
}

@Preview
@Composable
private fun WorkoutPlannerScreenPreview() {
    val viewModel = remember { WorkoutPlannerViewModel() }
    PreviewSurface {
        WorkoutPlannerScreen(viewModel = viewModel)
    }
}
