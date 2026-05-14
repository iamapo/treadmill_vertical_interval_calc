package com.redred.treadmillvertiintervallcalc.presentation

sealed interface WorkoutPlannerEvent {
    data class InputChanged(
        val field: WorkoutInputField,
        val value: String
    ) : WorkoutPlannerEvent

    data class ToggleSegmentCompleted(val segmentId: String) : WorkoutPlannerEvent
    data class SelectScreen(val screen: PlannerScreen) : WorkoutPlannerEvent
    data object GenerateWorkout : WorkoutPlannerEvent
    data object ResetChecklist : WorkoutPlannerEvent
    data object CopyWorkoutText : WorkoutPlannerEvent
}
