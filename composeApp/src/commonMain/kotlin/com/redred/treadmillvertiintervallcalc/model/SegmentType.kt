package com.redred.treadmillvertiintervallcalc.model

import kotlinx.serialization.Serializable

@Serializable
enum class SegmentType {
    WARMUP,
    HARD_INTERVAL,
    RECOVERY,
    STEADY_CLIMB,
    COOLDOWN
}
