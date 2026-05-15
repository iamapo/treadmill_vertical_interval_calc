package com.redred.treadmillvertiintervallcalc.presentation

import kotlinx.serialization.Serializable

@Serializable
data class ManualSegmentInput(
    val id: String,
    val type: ManualSegmentType,
    val durationMinutes: String,
    val pace: String,
    val inclinePercent: String,
    val repeats: String = "1",
    val recoveryDurationMinutes: String = "",
    val recoveryPace: String = "",
    val recoveryInclinePercent: String = ""
)
