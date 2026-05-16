package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.runtime.Composable
import com.redred.treadmillvertiintervallcalc.presentation.ManualSegmentType
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource
import verticaltreadmillrun.composeapp.generated.resources.Res
import verticaltreadmillrun.composeapp.generated.resources.manual_type_cooldown
import verticaltreadmillrun.composeapp.generated.resources.manual_type_repetition
import verticaltreadmillrun.composeapp.generated.resources.manual_type_single
import verticaltreadmillrun.composeapp.generated.resources.manual_type_warmup
import verticaltreadmillrun.composeapp.generated.resources.vr_ic_flame
import verticaltreadmillrun.composeapp.generated.resources.vr_ic_mountain
import verticaltreadmillrun.composeapp.generated.resources.vr_ic_repeat
import verticaltreadmillrun.composeapp.generated.resources.vr_ic_snowflake

@Composable
internal fun manualSegmentTypeLabel(type: ManualSegmentType): String =
    when (type) {
        ManualSegmentType.WARMUP -> stringResource(Res.string.manual_type_warmup)
        ManualSegmentType.REPETITION -> stringResource(Res.string.manual_type_repetition)
        ManualSegmentType.SINGLE -> stringResource(Res.string.manual_type_single)
        ManualSegmentType.COOLDOWN -> stringResource(Res.string.manual_type_cooldown)
    }

internal fun manualSegmentIconResource(type: ManualSegmentType): DrawableResource =
    when (type) {
        ManualSegmentType.WARMUP -> Res.drawable.vr_ic_flame
        ManualSegmentType.REPETITION -> Res.drawable.vr_ic_repeat
        ManualSegmentType.SINGLE -> Res.drawable.vr_ic_mountain
        ManualSegmentType.COOLDOWN -> Res.drawable.vr_ic_snowflake
    }

internal fun manualSegmentTypeDescription(type: ManualSegmentType): String =
    when (type) {
        ManualSegmentType.WARMUP -> "Bereite dich auf die Belastung vor"
        ManualSegmentType.REPETITION -> "Wiederhole eine Belastung mehrmals"
        ManualSegmentType.SINGLE -> "Füge ein einzelnes Belastungssegment hinzu"
        ManualSegmentType.COOLDOWN -> "Lasse die Belastung kontrolliert ausklingen"
    }
