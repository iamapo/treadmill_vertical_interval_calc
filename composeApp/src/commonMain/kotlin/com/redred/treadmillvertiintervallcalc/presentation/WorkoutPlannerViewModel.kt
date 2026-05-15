package com.redred.treadmillvertiintervallcalc.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.redred.treadmillvertiintervallcalc.domain.PaceParser
import com.redred.treadmillvertiintervallcalc.domain.WorkoutGenerator
import com.redred.treadmillvertiintervallcalc.model.SegmentType
import com.redred.treadmillvertiintervallcalc.model.WorkoutSegment
import com.redred.treadmillvertiintervallcalc.model.WorkoutInput
import com.redred.treadmillvertiintervallcalc.model.WorkoutPlan

class WorkoutPlannerViewModel(
    private val workoutGenerator: WorkoutGenerator = WorkoutGenerator(),
    private val plannerStateStore: PlannerStateStore? = null
) : ViewModel() {
    var state by mutableStateOf(WorkoutPlannerState())
        private set

    init {
        restorePersistedState()
        recalculateManualSummary()
    }

    fun onEvent(event: WorkoutPlannerEvent) {
        when (event) {
            is WorkoutPlannerEvent.InputChanged -> updateInput(event.field, event.value)
            is WorkoutPlannerEvent.SelectPlanningMode -> updatePlanningMode(event.mode)
            is WorkoutPlannerEvent.ManualSegmentDurationChanged -> updateManualSegment(event.segmentId) {
                it.copy(durationMinutes = event.value)
            }
            is WorkoutPlannerEvent.ManualSegmentPaceChanged -> updateManualSegment(event.segmentId) {
                it.copy(pace = event.value)
            }
            is WorkoutPlannerEvent.ManualSegmentInclineChanged -> updateManualSegment(event.segmentId) {
                it.copy(inclinePercent = event.value)
            }
            is WorkoutPlannerEvent.ManualSegmentRepeatsChanged -> updateManualSegment(event.segmentId) {
                it.copy(repeats = event.value)
            }
            is WorkoutPlannerEvent.ManualSegmentRecoveryDurationChanged -> updateManualSegment(event.segmentId) {
                it.copy(recoveryDurationMinutes = event.value)
            }
            is WorkoutPlannerEvent.ManualSegmentRecoveryPaceChanged -> updateManualSegment(event.segmentId) {
                it.copy(recoveryPace = event.value)
            }
            is WorkoutPlannerEvent.ManualSegmentRecoveryInclineChanged -> updateManualSegment(event.segmentId) {
                it.copy(recoveryInclinePercent = event.value)
            }
            is WorkoutPlannerEvent.ManualSegmentTypeChanged -> updateManualSegment(event.segmentId) {
                it.copy(type = event.type)
            }
            WorkoutPlannerEvent.ToggleAddManualSegmentTypePicker -> {
                state = state.copy(showAddSegmentTypePicker = !state.showAddSegmentTypePicker)
            }
            is WorkoutPlannerEvent.AddManualSegment -> addManualSegment(
                type = event.type,
                durationMinutes = event.durationMinutes,
                pace = event.pace,
                inclinePercent = event.inclinePercent,
                repeats = event.repeats,
                recoveryDurationMinutes = event.recoveryDurationMinutes,
                recoveryPace = event.recoveryPace,
                recoveryInclinePercent = event.recoveryInclinePercent
            )
            is WorkoutPlannerEvent.RemoveManualSegment -> removeManualSegment(event.segmentId)
            is WorkoutPlannerEvent.ToggleSegmentCompleted -> toggleSegmentCompleted(event.segmentId)
            is WorkoutPlannerEvent.SelectScreen -> state = state.copy(selectedScreen = event.screen)
            WorkoutPlannerEvent.GenerateWorkout -> generateWorkout()
            WorkoutPlannerEvent.ClearSavedState -> clearSavedState()
            WorkoutPlannerEvent.ResetChecklist -> resetChecklist()
            WorkoutPlannerEvent.CopyWorkoutText -> copyWorkoutText()
        }
        persistState()
    }

    fun generateWorkout() {
        if (state.planningMode == PlanningMode.MANUAL) {
            generateManualWorkout()
            return
        }

        val validation = validateInput(state)
        if (validation.input == null) {
            state = state.copy(
                validationErrors = validation.errors,
                selectedScreen = PlannerScreen.INPUT
            )
            return
        }

        val plan = workoutGenerator.generate(validation.input)
        state = state.withPlan(plan).copy(
            validationErrors = emptyMap(),
            selectedScreen = PlannerScreen.GENERATED,
            copiedTextPreview = ""
        )
    }

    private fun updatePlanningMode(mode: PlanningMode) {
        state = state.copy(planningMode = mode)
        if (mode == PlanningMode.MANUAL) {
            recalculateManualSummary()
        }
    }

    fun toggleSegmentCompleted(segmentId: String) {
        val currentPlan = state.generatedWorkoutPlan ?: return
        val updatedPlan = currentPlan.copy(
            segments = currentPlan.segments.map { segment ->
                if (segment.id == segmentId) segment.copy(isCompleted = !segment.isCompleted) else segment
            }
        )
        state = state.withPlan(updatedPlan)
    }

    fun resetChecklist() {
        val currentPlan = state.generatedWorkoutPlan ?: return
        val resetPlan = currentPlan.copy(
            segments = currentPlan.segments.map { it.copy(isCompleted = false) }
        )
        state = state.withPlan(resetPlan)
    }

    fun copyWorkoutText() {
        val text = state.generatedWorkoutPlan?.toAppleNotesText().orEmpty()
        if (text.isBlank()) return
        state = state.copy(
            copiedTextPreview = text,
            copyTextVersion = state.copyTextVersion + 1
        )
    }

    private fun updateInput(field: WorkoutInputField, value: String) {
        state = when (field) {
            WorkoutInputField.TARGET_ELEVATION -> state.copy(targetElevation = value)
            WorkoutInputField.TOTAL_DURATION -> state.copy(totalDuration = value)
            WorkoutInputField.WARM_UP_DURATION -> state.copy(warmUpDuration = value)
            WorkoutInputField.COOL_DOWN_DURATION -> state.copy(coolDownDuration = value)
            WorkoutInputField.MAX_INCLINE -> state.copy(maxIncline = value)
            WorkoutInputField.INCLINE_STEP -> state.copy(inclineStep = value)
            WorkoutInputField.HARD_INTERVAL_DURATION -> state.copy(hardIntervalDuration = value)
            WorkoutInputField.RECOVERY_INTERVAL_DURATION -> state.copy(recoveryIntervalDuration = value)
            WorkoutInputField.HARD_INTERVAL_PACE -> state.copy(hardIntervalPace = value)
            WorkoutInputField.RECOVERY_PACE -> state.copy(recoveryPace = value)
            WorkoutInputField.WARM_UP_PACE -> state.copy(warmUpPace = value)
            WorkoutInputField.COOL_DOWN_PACE -> state.copy(coolDownPace = value)
            WorkoutInputField.PREFERRED_HARD_INCLINE -> state.copy(preferredHardIncline = value)
            WorkoutInputField.PREFERRED_RECOVERY_INCLINE -> state.copy(preferredRecoveryIncline = value)
        }.copy(validationErrors = state.validationErrors - field)
    }

    private fun updateManualSegment(segmentId: String, transform: (ManualSegmentInput) -> ManualSegmentInput) {
        state = state.copy(
            manualSegments = state.manualSegments.map { segment ->
                if (segment.id == segmentId) transform(segment) else segment
            }
        )
        recalculateManualSummary()
    }

    private fun addManualSegment(
        type: ManualSegmentType,
        durationMinutes: String,
        pace: String,
        inclinePercent: String,
        repeats: String,
        recoveryDurationMinutes: String,
        recoveryPace: String,
        recoveryInclinePercent: String
    ) {
        val index = state.manualSegments.size + 1
        state = state.copy(
            manualSegments = state.manualSegments + ManualSegmentInput(
                id = "manual_custom_$index",
                type = type,
                durationMinutes = durationMinutes.trim(),
                pace = pace.trim(),
                inclinePercent = inclinePercent.trim(),
                repeats = repeats.trim().ifBlank { "1" },
                recoveryDurationMinutes = recoveryDurationMinutes.trim(),
                recoveryPace = recoveryPace.trim(),
                recoveryInclinePercent = recoveryInclinePercent.trim()
            ),
            showAddSegmentTypePicker = false
        )
        recalculateManualSummary()
    }

    private fun removeManualSegment(segmentId: String) {
        state = state.copy(manualSegments = state.manualSegments.filterNot { it.id == segmentId })
        recalculateManualSummary()
    }

    private fun recalculateManualSummary() {
        val segments = buildManualSegments(state.manualSegments, failOnInvalid = false)
        val totalDuration = segments.sumOf { it.durationMinutes }
        val totalDistance = segments.sumOf { segment ->
            if (segment.paceMinutesPerKm > 0.0) segment.durationMinutes / segment.paceMinutesPerKm else 0.0
        }
        state = state.copy(
            manualTotalDurationMinutes = totalDuration,
            manualTotalDistanceKilometers = totalDistance,
            manualAveragePaceMinutesPerKm = if (totalDistance > 0.0) totalDuration / totalDistance else 0.0,
            manualElevationMeters = segments.sumOf { it.elevationMeters }
        )
    }

    private fun generateManualWorkout() {
        val segments = buildManualSegments(state.manualSegments, failOnInvalid = true)
        if (segments.isEmpty()) {
            state = state.copy(selectedScreen = PlannerScreen.INPUT)
            return
        }

        val totalDuration = segments.sumOf { it.durationMinutes }
        val targetElevation = segments.sumOf { it.elevationMeters }
        val plan = WorkoutPlan(
            targetElevationMeters = targetElevation,
            totalDurationMinutes = totalDuration,
            segments = segments
        )
        state = state.withPlan(plan).copy(
            validationErrors = emptyMap(),
            selectedScreen = PlannerScreen.GENERATED,
            copiedTextPreview = ""
        )
    }

    private fun buildManualSegments(
        inputs: List<ManualSegmentInput>,
        failOnInvalid: Boolean
    ): List<WorkoutSegment> {
        var cumulativeElevation = 0.0
        var generatedId = 0
        val segments = mutableListOf<WorkoutSegment>()

        inputs.forEachIndexed { index, input ->
            val duration = input.durationMinutes.toPositiveIntOrNull()
                ?: if (failOnInvalid) return emptyList() else return@forEachIndexed
            val paceValue = PaceParser.parseMinutesPerKm(input.pace)
                ?: if (failOnInvalid) return emptyList() else return@forEachIndexed
            val incline = input.inclinePercent.toNonNegativeDoubleOrNull() ?: 0.0
            val repeats = if (input.type == ManualSegmentType.REPETITION) {
                input.repeats.toPositiveIntOrNull()
                    ?: if (failOnInvalid) return emptyList() else return@forEachIndexed
            } else {
                1
            }
            val recoveryDuration = if (input.type == ManualSegmentType.REPETITION) {
                input.recoveryDurationMinutes.toPositiveIntOrNull()
                    ?: if (failOnInvalid) return emptyList() else return@forEachIndexed
            } else {
                0
            }
            val recoveryPaceValue = if (input.type == ManualSegmentType.REPETITION) {
                PaceParser.parseMinutesPerKm(input.recoveryPace)
                    ?: if (failOnInvalid) return emptyList() else return@forEachIndexed
            } else {
                0.0
            }
            val recoveryIncline = if (input.type == ManualSegmentType.REPETITION) {
                input.recoveryInclinePercent.toNonNegativeDoubleOrNull()
                    ?: if (failOnInvalid) return emptyList() else return@forEachIndexed
            } else {
                0.0
            }

            repeat(repeats) { repeatIndex ->
                generatedId += 1
                val segmentDistance = duration / paceValue
                val segmentElevation = segmentDistance * 1000.0 * (incline / 100.0)
                cumulativeElevation += segmentElevation
                val title = when (input.type) {
                    ManualSegmentType.WARMUP -> "Warm-up"
                    ManualSegmentType.REPETITION -> "Repetition ${repeatIndex + 1}"
                    ManualSegmentType.SINGLE -> "Segment ${index + 1}"
                    ManualSegmentType.COOLDOWN -> "Cool-down"
                }

                segments += WorkoutSegment(
                    id = "manual_$generatedId",
                    title = title,
                    durationMinutes = duration,
                    pace = input.pace.trim(),
                    paceMinutesPerKm = paceValue,
                    inclinePercent = incline,
                    elevationMeters = segmentElevation,
                    cumulativeElevationMeters = cumulativeElevation,
                    type = input.type.mappedType
                )

                if (input.type == ManualSegmentType.REPETITION) {
                    generatedId += 1
                    val recoveryDistance = recoveryDuration / recoveryPaceValue
                    val recoveryElevation = recoveryDistance * 1000.0 * (recoveryIncline / 100.0)
                    cumulativeElevation += recoveryElevation
                    segments += WorkoutSegment(
                        id = "manual_$generatedId",
                        title = "Recovery ${repeatIndex + 1}",
                        durationMinutes = recoveryDuration,
                        pace = input.recoveryPace.trim(),
                        paceMinutesPerKm = recoveryPaceValue,
                        inclinePercent = recoveryIncline,
                        elevationMeters = recoveryElevation,
                        cumulativeElevationMeters = cumulativeElevation,
                        type = SegmentType.RECOVERY
                    )
                }
            }
        }

        return segments
    }

    private fun validateInput(state: WorkoutPlannerState): ValidationResult {
        val errors = mutableMapOf<WorkoutInputField, String>()

        val targetElevation = state.targetElevation.toPositiveDoubleOrNull()
            ?: errors.putAndReturn(WorkoutInputField.TARGET_ELEVATION, "Enter a target above 0 m.")
        val totalDuration = state.totalDuration.toPositiveIntOrNull()
            ?: errors.putAndReturn(WorkoutInputField.TOTAL_DURATION, "Enter total minutes above 0.")
        val warmUpDuration = state.warmUpDuration.toNonNegativeIntOrNull()
            ?: errors.putAndReturn(WorkoutInputField.WARM_UP_DURATION, "Enter 0 or more minutes.")
        val coolDownDuration = state.coolDownDuration.toNonNegativeIntOrNull()
            ?: errors.putAndReturn(WorkoutInputField.COOL_DOWN_DURATION, "Enter 0 or more minutes.")
        val maxIncline = state.maxIncline.toPositiveDoubleOrNull()
            ?: errors.putAndReturn(WorkoutInputField.MAX_INCLINE, "Enter a max incline above 0%.")
        val inclineStep = state.inclineStep.toPositiveDoubleOrNull()
            ?: errors.putAndReturn(WorkoutInputField.INCLINE_STEP, "Enter a step size above 0%.")
        val hardIntervalDuration = state.hardIntervalDuration.toPositiveIntOrNull()
            ?: errors.putAndReturn(WorkoutInputField.HARD_INTERVAL_DURATION, "Enter hard interval minutes above 0.")
        val recoveryIntervalDuration = state.recoveryIntervalDuration.toPositiveIntOrNull()
            ?: errors.putAndReturn(WorkoutInputField.RECOVERY_INTERVAL_DURATION, "Enter recovery minutes above 0.")
        val hardPace = PaceParser.parseMinutesPerKm(state.hardIntervalPace)
            ?: errors.putAndReturn(WorkoutInputField.HARD_INTERVAL_PACE, "Use mm:ss, for example 8:30.")
        val recoveryPace = PaceParser.parseMinutesPerKm(state.recoveryPace)
            ?: errors.putAndReturn(WorkoutInputField.RECOVERY_PACE, "Use mm:ss, for example 9:30.")
        val warmUpPace = PaceParser.parseMinutesPerKm(state.warmUpPace)
            ?: errors.putAndReturn(WorkoutInputField.WARM_UP_PACE, "Use mm:ss, for example 7:30.")
        val coolDownPace = PaceParser.parseMinutesPerKm(state.coolDownPace)
            ?: errors.putAndReturn(WorkoutInputField.COOL_DOWN_PACE, "Use mm:ss, for example 8:30.")
        val preferredHardIncline = state.preferredHardIncline.toNonNegativeDoubleOrNull()
            ?: errors.putAndReturn(WorkoutInputField.PREFERRED_HARD_INCLINE, "Enter 0% or more.")
        val preferredRecoveryIncline = state.preferredRecoveryIncline.toNonNegativeDoubleOrNull()
            ?: errors.putAndReturn(WorkoutInputField.PREFERRED_RECOVERY_INCLINE, "Enter 0% or more.")

        if (totalDuration != null && warmUpDuration != null && coolDownDuration != null &&
            totalDuration <= warmUpDuration + coolDownDuration
        ) {
            errors[WorkoutInputField.TOTAL_DURATION] = "Total duration must be longer than warm-up plus cool-down."
        }

        if (maxIncline != null && inclineStep != null && inclineStep > maxIncline) {
            errors[WorkoutInputField.INCLINE_STEP] = "Step size must not exceed max incline."
        }

        if (maxIncline != null && preferredHardIncline != null && preferredHardIncline > maxIncline) {
            errors[WorkoutInputField.PREFERRED_HARD_INCLINE] = "Hard incline must not exceed max incline."
        }

        if (maxIncline != null && preferredRecoveryIncline != null && preferredRecoveryIncline > maxIncline) {
            errors[WorkoutInputField.PREFERRED_RECOVERY_INCLINE] = "Recovery incline must not exceed max incline."
        }

        val input = if (errors.isEmpty()) {
            WorkoutInput(
                targetElevationMeters = requireNotNull(targetElevation),
                totalDurationMinutes = requireNotNull(totalDuration),
                warmUpDurationMinutes = requireNotNull(warmUpDuration),
                coolDownDurationMinutes = requireNotNull(coolDownDuration),
                maxInclinePercent = requireNotNull(maxIncline),
                inclineStepPercent = requireNotNull(inclineStep),
                hardIntervalDurationMinutes = requireNotNull(hardIntervalDuration),
                recoveryIntervalDurationMinutes = requireNotNull(recoveryIntervalDuration),
                hardIntervalPace = state.hardIntervalPace.trim(),
                hardIntervalPaceMinutesPerKm = requireNotNull(hardPace),
                recoveryPace = state.recoveryPace.trim(),
                recoveryPaceMinutesPerKm = requireNotNull(recoveryPace),
                warmUpPace = state.warmUpPace.trim(),
                warmUpPaceMinutesPerKm = requireNotNull(warmUpPace),
                coolDownPace = state.coolDownPace.trim(),
                coolDownPaceMinutesPerKm = requireNotNull(coolDownPace),
                preferredHardInclinePercent = requireNotNull(preferredHardIncline),
                preferredRecoveryInclinePercent = requireNotNull(preferredRecoveryIncline)
            )
        } else {
            null
        }

        return ValidationResult(input = input, errors = errors)
    }

    private fun restorePersistedState() {
        val json = plannerStateStore?.load() ?: return
        val restored = snapshotJsonToState(json) ?: return
        state = restored
    }

    private fun persistState() {
        val json = state.toSnapshotJson()
        plannerStateStore?.save(json)
    }

    private fun clearSavedState() {
        plannerStateStore?.clear()
        state = WorkoutPlannerState()
        recalculateManualSummary()
    }
}

private data class ValidationResult(
    val input: WorkoutInput?,
    val errors: Map<WorkoutInputField, String>
)

private fun WorkoutPlannerState.withPlan(plan: WorkoutPlan): WorkoutPlannerState =
    copy(
        generatedWorkoutPlan = plan,
        totalPlannedElevation = plan.plannedElevationMeters,
        targetDifference = plan.targetDifferenceMeters,
        completedElevation = plan.completedElevationMeters,
        completedDuration = plan.completedDurationMinutes
    )

private fun String.toPositiveDoubleOrNull(): Double? =
    normalizedNumber().toDoubleOrNull()?.takeIf { it > 0.0 }

private fun String.toNonNegativeDoubleOrNull(): Double? =
    normalizedNumber().toDoubleOrNull()?.takeIf { it >= 0.0 }

private fun String.toPositiveIntOrNull(): Int? =
    trim().toIntOrNull()?.takeIf { it > 0 }

private fun String.toNonNegativeIntOrNull(): Int? =
    trim().toIntOrNull()?.takeIf { it >= 0 }

private fun String.normalizedNumber(): String = trim().replace(",", ".")

private fun <K> MutableMap<K, String>.putAndReturn(key: K, value: String): Nothing? {
    this[key] = value
    return null
}
