package com.redred.treadmillvertiintervallcalc.presentation

import com.redred.treadmillvertiintervallcalc.model.WorkoutPlan
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

private val plannerJson = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
}

@Serializable
private data class PlannerSnapshot(
    val planningMode: PlanningMode,
    val targetElevation: String,
    val totalDuration: String,
    val warmUpDuration: String,
    val coolDownDuration: String,
    val maxIncline: String,
    val inclineStep: String,
    val hardIntervalDuration: String,
    val recoveryIntervalDuration: String,
    val hardIntervalPace: String,
    val recoveryPace: String,
    val warmUpPace: String,
    val coolDownPace: String,
    val preferredHardIncline: String,
    val preferredRecoveryIncline: String,
    val selectedScreen: PlannerScreen,
    val generatedWorkoutPlan: WorkoutPlan?,
    val manualSegments: List<ManualSegmentInput>,
    val showAddSegmentTypePicker: Boolean
)

internal fun WorkoutPlannerState.toSnapshotJson(): String {
    val snapshot = PlannerSnapshot(
        planningMode = planningMode,
        targetElevation = targetElevation,
        totalDuration = totalDuration,
        warmUpDuration = warmUpDuration,
        coolDownDuration = coolDownDuration,
        maxIncline = maxIncline,
        inclineStep = inclineStep,
        hardIntervalDuration = hardIntervalDuration,
        recoveryIntervalDuration = recoveryIntervalDuration,
        hardIntervalPace = hardIntervalPace,
        recoveryPace = recoveryPace,
        warmUpPace = warmUpPace,
        coolDownPace = coolDownPace,
        preferredHardIncline = preferredHardIncline,
        preferredRecoveryIncline = preferredRecoveryIncline,
        selectedScreen = selectedScreen,
        generatedWorkoutPlan = generatedWorkoutPlan,
        manualSegments = manualSegments,
        showAddSegmentTypePicker = showAddSegmentTypePicker
    )
    return plannerJson.encodeToString(PlannerSnapshot.serializer(), snapshot)
}

internal fun snapshotJsonToState(json: String): WorkoutPlannerState? {
    return runCatching {
        val snapshot = plannerJson.decodeFromString(PlannerSnapshot.serializer(), json)
        WorkoutPlannerState(
            planningMode = snapshot.planningMode,
            targetElevation = snapshot.targetElevation,
            totalDuration = snapshot.totalDuration,
            warmUpDuration = snapshot.warmUpDuration,
            coolDownDuration = snapshot.coolDownDuration,
            maxIncline = snapshot.maxIncline,
            inclineStep = snapshot.inclineStep,
            hardIntervalDuration = snapshot.hardIntervalDuration,
            recoveryIntervalDuration = snapshot.recoveryIntervalDuration,
            hardIntervalPace = snapshot.hardIntervalPace,
            recoveryPace = snapshot.recoveryPace,
            warmUpPace = snapshot.warmUpPace,
            coolDownPace = snapshot.coolDownPace,
            preferredHardIncline = snapshot.preferredHardIncline,
            preferredRecoveryIncline = snapshot.preferredRecoveryIncline,
            selectedScreen = snapshot.selectedScreen,
            generatedWorkoutPlan = snapshot.generatedWorkoutPlan,
            manualSegments = snapshot.manualSegments,
            showAddSegmentTypePicker = snapshot.showAddSegmentTypePicker
        )
    }.getOrNull()
}
