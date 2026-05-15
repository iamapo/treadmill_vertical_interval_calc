package com.redred.treadmillvertiintervallcalc.presentation

sealed interface WorkoutPlannerEvent {
    data class InputChanged(
        val field: WorkoutInputField,
        val value: String
    ) : WorkoutPlannerEvent
    data class SelectPlanningMode(val mode: PlanningMode) : WorkoutPlannerEvent
    data class ManualSegmentDurationChanged(val segmentId: String, val value: String) : WorkoutPlannerEvent
    data class ManualSegmentPaceChanged(val segmentId: String, val value: String) : WorkoutPlannerEvent
    data class ManualSegmentInclineChanged(val segmentId: String, val value: String) : WorkoutPlannerEvent
    data class ManualSegmentRepeatsChanged(val segmentId: String, val value: String) : WorkoutPlannerEvent
    data class ManualSegmentRecoveryDurationChanged(val segmentId: String, val value: String) : WorkoutPlannerEvent
    data class ManualSegmentRecoveryPaceChanged(val segmentId: String, val value: String) : WorkoutPlannerEvent
    data class ManualSegmentRecoveryInclineChanged(val segmentId: String, val value: String) : WorkoutPlannerEvent
    data class ManualSegmentTypeChanged(val segmentId: String, val type: ManualSegmentType) : WorkoutPlannerEvent
    data object ToggleAddManualSegmentTypePicker : WorkoutPlannerEvent
    data class AddManualSegment(
        val type: ManualSegmentType,
        val durationMinutes: String,
        val pace: String,
        val inclinePercent: String,
        val repeats: String = "1",
        val recoveryDurationMinutes: String = "",
        val recoveryPace: String = "",
        val recoveryInclinePercent: String = ""
    ) : WorkoutPlannerEvent
    data class RemoveManualSegment(val segmentId: String) : WorkoutPlannerEvent

    data class ToggleSegmentCompleted(val segmentId: String) : WorkoutPlannerEvent
    data class SelectScreen(val screen: PlannerScreen) : WorkoutPlannerEvent
    data object GenerateWorkout : WorkoutPlannerEvent
    data object ClearSavedState : WorkoutPlannerEvent
    data object ResetChecklist : WorkoutPlannerEvent
    data object CopyWorkoutText : WorkoutPlannerEvent
}
