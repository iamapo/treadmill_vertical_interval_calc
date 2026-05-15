package com.redred.treadmillvertiintervallcalc.presentation

import com.redred.treadmillvertiintervallcalc.model.SegmentType
import kotlinx.serialization.Serializable

@Serializable
enum class ManualSegmentType(val mappedType: SegmentType) {
    WARMUP(SegmentType.WARMUP),
    REPETITION(SegmentType.HARD_INTERVAL),
    SINGLE(SegmentType.STEADY_CLIMB),
    COOLDOWN(SegmentType.COOLDOWN)
}
