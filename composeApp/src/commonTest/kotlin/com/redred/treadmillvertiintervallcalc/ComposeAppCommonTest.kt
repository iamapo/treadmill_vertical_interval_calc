package com.redred.treadmillvertiintervallcalc

import com.redred.treadmillvertiintervallcalc.domain.ElevationCalculator
import com.redred.treadmillvertiintervallcalc.domain.PaceParser
import com.redred.treadmillvertiintervallcalc.domain.WorkoutGenerator
import com.redred.treadmillvertiintervallcalc.model.WorkoutInput
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.math.abs

class ComposeAppCommonTest {

    @Test
    fun parsesPaceAsDecimalMinutesPerKilometer() {
        assertEquals(8.5, PaceParser.parseMinutesPerKm("8:30"))
        assertEquals(6.5, PaceParser.parseMinutesPerKm("6:30"))
        assertEquals(10.0, PaceParser.parseMinutesPerKm("10:00"))
        assertEquals(null, PaceParser.parseMinutesPerKm("8:75"))
    }

    @Test
    fun calculatesElevationFromDurationPaceAndIncline() {
        val hardElevation = ElevationCalculator.elevationMeters(
            durationMinutes = 5,
            paceMinutesPerKm = 8.5,
            inclinePercent = 15.0
        )
        val recoveryElevation = ElevationCalculator.elevationMeters(
            durationMinutes = 3,
            paceMinutesPerKm = 9.5,
            inclinePercent = 5.0
        )

        assertTrue(abs(hardElevation - 88.2) < 0.1)
        assertTrue(abs(recoveryElevation - 15.8) < 0.1)
    }

    @Test
    fun generatorCreatesFullDurationPlanCloseToTarget() {
        val input = WorkoutInput(
            targetElevationMeters = 1200.0,
            totalDurationMinutes = 120,
            warmUpDurationMinutes = 10,
            coolDownDurationMinutes = 10,
            maxInclinePercent = 15.0,
            inclineStepPercent = 0.5,
            hardIntervalDurationMinutes = 5,
            recoveryIntervalDurationMinutes = 3,
            hardIntervalPace = "8:30",
            hardIntervalPaceMinutesPerKm = assertNotNull(PaceParser.parseMinutesPerKm("8:30")),
            recoveryPace = "9:30",
            recoveryPaceMinutesPerKm = assertNotNull(PaceParser.parseMinutesPerKm("9:30")),
            warmUpPace = "7:30",
            warmUpPaceMinutesPerKm = assertNotNull(PaceParser.parseMinutesPerKm("7:30")),
            coolDownPace = "8:30",
            coolDownPaceMinutesPerKm = assertNotNull(PaceParser.parseMinutesPerKm("8:30")),
            preferredHardInclinePercent = 15.0,
            preferredRecoveryInclinePercent = 5.0
        )

        val plan = WorkoutGenerator().generate(input)

        assertEquals(120, plan.segments.sumOf { it.durationMinutes })
        assertTrue(plan.segments.all { it.inclinePercent <= 15.0 })
        assertTrue(abs(plan.targetDifferenceMeters) < 10.0)
        assertTrue(plan.totalDistanceKilometers > 0.0)
        assertTrue(plan.averagePaceMinutesPerKm > 0.0)
    }
}
