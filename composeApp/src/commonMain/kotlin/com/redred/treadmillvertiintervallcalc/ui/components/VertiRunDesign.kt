package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import com.redred.treadmillvertiintervallcalc.model.SegmentType
import com.redred.treadmillvertiintervallcalc.model.WorkoutSegment
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource
import verticaltreadmillrun.composeapp.generated.resources.*

internal val VertiGreen = Color(0xFF007A3D)
internal val VertiGreenDark = Color(0xFF006B36)
internal val VertiGreenSoft = Color(0xFFEAF4EE)
internal val VertiText = Color(0xFF15171D)
internal val VertiMuted = Color(0xFF6F7480)
internal val VertiLine = Color(0xFFDDE4E0)
internal val VertiDanger = Color(0xFFD71920)
internal val VertiPage = Color(0xFFF7F9F8)

internal val VertiCardShape = RoundedCornerShape(14.dp)
internal val VertiButtonShape = RoundedCornerShape(12.dp)
internal val VertiPillShape = RoundedCornerShape(999.dp)

internal val VertiGreenBrush = Brush.horizontalGradient(
    listOf(Color(0xFF007235), Color(0xFF008D48), Color(0xFF006D38))
)

internal fun segmentIconResource(type: SegmentType): DrawableResource =
    when (type) {
        SegmentType.WARMUP -> Res.drawable.vr_ic_flame
        SegmentType.HARD_INTERVAL -> Res.drawable.vr_ic_interval_bars
        SegmentType.RECOVERY -> Res.drawable.vr_ic_heart_recovery
        SegmentType.STEADY_CLIMB -> Res.drawable.vr_ic_mountain
        SegmentType.COOLDOWN -> Res.drawable.vr_ic_snowflake
    }

@Composable
internal fun localizedSegmentTitle(segment: WorkoutSegment): String =
    when (segment.type) {
        SegmentType.WARMUP -> stringResource(Res.string.segment_warmup)
        SegmentType.HARD_INTERVAL -> stringResource(
            Res.string.segment_hard_interval,
            segment.title.trailingNumberOrOne()
        )
        SegmentType.RECOVERY -> stringResource(
            Res.string.segment_recovery,
            segment.title.trailingNumberOrOne()
        )
        SegmentType.STEADY_CLIMB -> if (segment.title.contains("warm", ignoreCase = true)) {
            stringResource(Res.string.segment_warmup_climb)
        } else {
            stringResource(Res.string.segment_steady_climb_adjustment)
        }
        SegmentType.COOLDOWN -> stringResource(Res.string.segment_cooldown)
    }

internal fun String.trailingNumberOrOne(): Int =
    substringAfterLast(' ', missingDelimiterValue = "")
        .toIntOrNull()
        ?: 1
