package com.redred.treadmillvertiintervallcalc.domain

import com.redred.treadmillvertiintervallcalc.model.SegmentType
import com.redred.treadmillvertiintervallcalc.model.WorkoutInput
import com.redred.treadmillvertiintervallcalc.model.WorkoutPlan
import com.redred.treadmillvertiintervallcalc.model.WorkoutSegment
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.roundToInt

class WorkoutGenerator {
    fun generate(input: WorkoutInput): WorkoutPlan {
        val step = input.inclineStepPercent
        val maxIncline = ElevationCalculator.oneDecimal(input.maxInclinePercent)
        val hardPreference = roundAndClamp(input.preferredHardInclinePercent, step, maxIncline)
        val recoveryPreference = roundAndClamp(input.preferredRecoveryInclinePercent, step, maxIncline)

        val warmUpDrafts = warmUpDrafts(input, recoveryPreference, step, maxIncline)
        val mainWorkoutMinutes = input.totalDurationMinutes -
            input.warmUpDurationMinutes -
            input.coolDownDurationMinutes
        val blockDuration = input.hardIntervalDurationMinutes + input.recoveryIntervalDurationMinutes
        val blockCount = mainWorkoutMinutes / blockDuration
        val adjustmentMinutes = mainWorkoutMinutes - blockCount * blockDuration

        val drafts = buildList {
            addAll(warmUpDrafts)
            repeat(blockCount) { index ->
                add(
                    SegmentDraft(
                        title = "Hard interval ${index + 1}",
                        durationMinutes = input.hardIntervalDurationMinutes,
                        pace = input.hardIntervalPace,
                        paceMinutesPerKm = input.hardIntervalPaceMinutesPerKm,
                        type = SegmentType.HARD_INTERVAL,
                        inclineRole = InclineRole.HARD
                    )
                )
                add(
                    SegmentDraft(
                        title = "Recovery ${index + 1}",
                        durationMinutes = input.recoveryIntervalDurationMinutes,
                        pace = input.recoveryPace,
                        paceMinutesPerKm = input.recoveryPaceMinutesPerKm,
                        type = SegmentType.RECOVERY,
                        inclineRole = InclineRole.RECOVERY
                    )
                )
            }
            if (adjustmentMinutes > 0) {
                add(
                    SegmentDraft(
                        title = "Steady climb adjustment",
                        durationMinutes = adjustmentMinutes,
                        pace = input.hardIntervalPace,
                        paceMinutesPerKm = input.hardIntervalPaceMinutesPerKm,
                        type = SegmentType.STEADY_CLIMB,
                        inclineRole = InclineRole.ADJUSTMENT
                    )
                )
            }
            if (input.coolDownDurationMinutes > 0) {
                add(
                    SegmentDraft(
                        title = "Cool-down",
                        durationMinutes = input.coolDownDurationMinutes,
                        pace = input.coolDownPace,
                        paceMinutesPerKm = input.coolDownPaceMinutesPerKm,
                        type = SegmentType.COOLDOWN,
                        inclineRole = InclineRole.Fixed(0.0)
                    )
                )
            }
        }

        val chosenInclines = chooseInclines(
            drafts = drafts,
            targetElevationMeters = input.targetElevationMeters,
            maxIncline = maxIncline,
            step = step,
            hardPreference = hardPreference,
            recoveryPreference = recoveryPreference
        )

        var cumulativeElevation = 0.0
        val segments = drafts.mapIndexed { index, draft ->
            val incline = when (draft.inclineRole) {
                InclineRole.HARD -> chosenInclines.hard
                InclineRole.RECOVERY -> chosenInclines.recovery
                InclineRole.ADJUSTMENT -> chosenInclines.adjustment
                is InclineRole.Fixed -> draft.inclineRole.inclinePercent
            }
            val elevation = ElevationCalculator.elevationMeters(
                durationMinutes = draft.durationMinutes,
                paceMinutesPerKm = draft.paceMinutesPerKm,
                inclinePercent = incline
            )
            cumulativeElevation += elevation
            WorkoutSegment(
                id = "segment-${index + 1}",
                title = draft.title,
                durationMinutes = draft.durationMinutes,
                pace = draft.pace,
                paceMinutesPerKm = draft.paceMinutesPerKm,
                inclinePercent = ElevationCalculator.oneDecimal(incline),
                elevationMeters = elevation,
                cumulativeElevationMeters = cumulativeElevation,
                type = draft.type
            )
        }

        return WorkoutPlan(
            targetElevationMeters = input.targetElevationMeters,
            totalDurationMinutes = input.totalDurationMinutes,
            segments = segments
        )
    }

    private fun warmUpDrafts(
        input: WorkoutInput,
        recoveryPreference: Double,
        step: Double,
        maxIncline: Double
    ): List<SegmentDraft> {
        if (input.warmUpDurationMinutes == 0) return emptyList()

        val easyIncline = roundAndClamp(2.0, step, maxIncline)
        if (input.warmUpDurationMinutes <= 10) {
            return listOf(
                SegmentDraft(
                    title = "Warm-up",
                    durationMinutes = input.warmUpDurationMinutes,
                    pace = input.warmUpPace,
                    paceMinutesPerKm = input.warmUpPaceMinutesPerKm,
                    type = SegmentType.WARMUP,
                    inclineRole = InclineRole.Fixed(easyIncline)
                )
            )
        }

        val climbMinutes = 5
        val easyMinutes = input.warmUpDurationMinutes - climbMinutes
        val climbIncline = roundAndClamp(recoveryPreference.coerceAtMost(4.0).coerceAtLeast(easyIncline), step, maxIncline)
        return listOf(
            SegmentDraft(
                title = "Warm-up",
                durationMinutes = easyMinutes,
                pace = input.warmUpPace,
                paceMinutesPerKm = input.warmUpPaceMinutesPerKm,
                type = SegmentType.WARMUP,
                inclineRole = InclineRole.Fixed(easyIncline)
            ),
            SegmentDraft(
                title = "Warm-up climb",
                durationMinutes = climbMinutes,
                pace = input.warmUpPace,
                paceMinutesPerKm = input.warmUpPaceMinutesPerKm,
                type = SegmentType.STEADY_CLIMB,
                inclineRole = InclineRole.Fixed(climbIncline)
            )
        )
    }

    private fun chooseInclines(
        drafts: List<SegmentDraft>,
        targetElevationMeters: Double,
        maxIncline: Double,
        step: Double,
        hardPreference: Double,
        recoveryPreference: Double
    ): ChosenInclines {
        val inclineValues = inclineValues(maxIncline, step)
        val fixedElevation = drafts
            .filter { it.inclineRole is InclineRole.Fixed }
            .sumOf { draft ->
                ElevationCalculator.elevationMeters(
                    durationMinutes = draft.durationMinutes,
                    paceMinutesPerKm = draft.paceMinutesPerKm,
                    inclinePercent = (draft.inclineRole as InclineRole.Fixed).inclinePercent
                )
            }

        val hardFactor = drafts.elevationFactorFor(InclineRole.HARD)
        val recoveryFactor = drafts.elevationFactorFor(InclineRole.RECOVERY)
        val adjustmentFactor = drafts.elevationFactorFor(InclineRole.ADJUSTMENT)

        var best = ScoredInclines(
            inclines = ChosenInclines(hardPreference, recoveryPreference, 0.0),
            score = Double.MAX_VALUE
        )

        // Hard and recovery inclines are chosen globally so repeated blocks stay readable.
        // The optional final adjustment incline is solved from the remaining elevation, then rounded to the treadmill step.
        for (hardIncline in inclineValues) {
            for (recoveryIncline in inclineValues) {
                val beforeAdjustment = fixedElevation +
                    hardFactor * hardIncline +
                    recoveryFactor * recoveryIncline
                val adjustmentIncline = if (adjustmentFactor > 0.0) {
                    roundAndClamp((targetElevationMeters - beforeAdjustment) / adjustmentFactor, step, maxIncline)
                } else {
                    0.0
                }
                val plannedElevation = beforeAdjustment + adjustmentFactor * adjustmentIncline
                val differencePenalty = abs(plannedElevation - targetElevationMeters)
                val preferencePenalty = abs(hardIncline - hardPreference) * 0.35 +
                    abs(recoveryIncline - recoveryPreference) * 0.35
                val intervalShapePenalty = if (hardIncline < recoveryIncline) 500.0 else 0.0
                val score = differencePenalty + preferencePenalty + intervalShapePenalty

                if (score < best.score) {
                    best = ScoredInclines(
                        inclines = ChosenInclines(hardIncline, recoveryIncline, adjustmentIncline),
                        score = score
                    )
                }
            }
        }

        return best.inclines
    }

    private fun List<SegmentDraft>.elevationFactorFor(role: InclineRole): Double =
        filter { it.inclineRole == role }
            .sumOf { draft -> draft.durationMinutes * 10.0 / draft.paceMinutesPerKm }

    private fun inclineValues(maxIncline: Double, step: Double): List<Double> {
        val count = floor(maxIncline / step).roundToInt()
        return (0..count)
            .map { ElevationCalculator.oneDecimal(it * step) }
            .let { values ->
                if (values.lastOrNull() == maxIncline) values else values + maxIncline
            }
            .distinct()
    }

    private fun roundAndClamp(value: Double, step: Double, maxIncline: Double): Double =
        ElevationCalculator.oneDecimal(
            ElevationCalculator.roundInclineToStep(value.coerceIn(0.0, maxIncline), step)
                .coerceIn(0.0, maxIncline)
        )
}

private data class SegmentDraft(
    val title: String,
    val durationMinutes: Int,
    val pace: String,
    val paceMinutesPerKm: Double,
    val type: SegmentType,
    val inclineRole: InclineRole
)

private sealed interface InclineRole {
    data object HARD : InclineRole
    data object RECOVERY : InclineRole
    data object ADJUSTMENT : InclineRole
    data class Fixed(val inclinePercent: Double) : InclineRole
}

private data class ChosenInclines(
    val hard: Double,
    val recovery: Double,
    val adjustment: Double
)

private data class ScoredInclines(
    val inclines: ChosenInclines,
    val score: Double
)
