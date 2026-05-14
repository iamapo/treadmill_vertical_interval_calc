package com.redred.treadmillvertiintervallcalc.presentation

import com.redred.treadmillvertiintervallcalc.model.WorkoutPlan

data class WorkoutPlannerState(
    val targetElevation: String = "1200",
    val totalDuration: String = "120",
    val warmUpDuration: String = "10",
    val coolDownDuration: String = "10",
    val maxIncline: String = "15",
    val inclineStep: String = "0.5",
    val hardIntervalDuration: String = "5",
    val recoveryIntervalDuration: String = "3",
    val hardIntervalPace: String = "8:30",
    val recoveryPace: String = "9:30",
    val warmUpPace: String = "7:30",
    val coolDownPace: String = "8:30",
    val preferredHardIncline: String = "15",
    val preferredRecoveryIncline: String = "5",
    val selectedScreen: PlannerScreen = PlannerScreen.INPUT,
    val generatedWorkoutPlan: WorkoutPlan? = null,
    val validationErrors: Map<WorkoutInputField, String> = emptyMap(),
    val totalPlannedElevation: Double = 0.0,
    val targetDifference: Double = 0.0,
    val completedElevation: Double = 0.0,
    val completedDuration: Int = 0,
    val copiedTextPreview: String = "",
    val copyTextVersion: Int = 0
) {
    fun valueFor(field: WorkoutInputField): String = when (field) {
        WorkoutInputField.TARGET_ELEVATION -> targetElevation
        WorkoutInputField.TOTAL_DURATION -> totalDuration
        WorkoutInputField.WARM_UP_DURATION -> warmUpDuration
        WorkoutInputField.COOL_DOWN_DURATION -> coolDownDuration
        WorkoutInputField.MAX_INCLINE -> maxIncline
        WorkoutInputField.INCLINE_STEP -> inclineStep
        WorkoutInputField.HARD_INTERVAL_DURATION -> hardIntervalDuration
        WorkoutInputField.RECOVERY_INTERVAL_DURATION -> recoveryIntervalDuration
        WorkoutInputField.HARD_INTERVAL_PACE -> hardIntervalPace
        WorkoutInputField.RECOVERY_PACE -> recoveryPace
        WorkoutInputField.WARM_UP_PACE -> warmUpPace
        WorkoutInputField.COOL_DOWN_PACE -> coolDownPace
        WorkoutInputField.PREFERRED_HARD_INCLINE -> preferredHardIncline
        WorkoutInputField.PREFERRED_RECOVERY_INCLINE -> preferredRecoveryIncline
    }
}
