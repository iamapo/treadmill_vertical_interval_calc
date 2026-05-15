package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redred.treadmillvertiintervallcalc.presentation.ManualSegmentInput
import com.redred.treadmillvertiintervallcalc.presentation.ManualSegmentType
import org.jetbrains.compose.resources.stringResource
import vertirun.composeapp.generated.resources.Res
import vertirun.composeapp.generated.resources.button_remove_segment
import vertirun.composeapp.generated.resources.field_manual_duration
import vertirun.composeapp.generated.resources.field_manual_incline_optional
import vertirun.composeapp.generated.resources.field_manual_pace
import vertirun.composeapp.generated.resources.field_manual_recovery_duration
import vertirun.composeapp.generated.resources.field_manual_recovery_incline
import vertirun.composeapp.generated.resources.field_manual_recovery_pace
import vertirun.composeapp.generated.resources.field_manual_repeats
import vertirun.composeapp.generated.resources.vr_ic_trash

@Composable
internal fun ManualSegmentCard(
    segment: ManualSegmentInput,
    onDurationChanged: (String) -> Unit,
    onPaceChanged: (String) -> Unit,
    onInclineChanged: (String) -> Unit,
    onRepeatsChanged: (String) -> Unit,
    onRecoveryDurationChanged: (String) -> Unit,
    onRecoveryPaceChanged: (String) -> Unit,
    onRecoveryInclineChanged: (String) -> Unit,
    onRemove: () -> Unit
) {
    VertiCard {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconBubble(manualSegmentIconResource(segment.type), Modifier.size(66.dp), selected = true)
                Text(
                    text = manualSegmentTypeLabel(segment.type),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 14.dp),
                    color = VertiText,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                TextButton(onClick = onRemove) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        VrIcon(resource = Res.drawable.vr_ic_trash, contentDescription = null, size = 18.dp, tint = VertiDanger)
                        Text(
                            text = stringResource(Res.string.button_remove_segment),
                            modifier = Modifier.padding(start = 6.dp),
                            color = VertiDanger,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                SmallManualField(segment.durationMinutes, onDurationChanged, stringResource(Res.string.field_manual_duration), KeyboardType.Number, Modifier.weight(1f))
                SmallManualField(segment.pace, onPaceChanged, stringResource(Res.string.field_manual_pace), KeyboardType.Text, Modifier.weight(1f))
                SmallManualField(segment.inclinePercent, onInclineChanged, stringResource(Res.string.field_manual_incline_optional), KeyboardType.Decimal, Modifier.weight(1f))
            }
            if (segment.type == ManualSegmentType.REPETITION) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SmallManualField(segment.repeats, onRepeatsChanged, stringResource(Res.string.field_manual_repeats), KeyboardType.Number, Modifier.weight(1f))
                    SmallManualField(segment.recoveryDurationMinutes, onRecoveryDurationChanged, stringResource(Res.string.field_manual_recovery_duration), KeyboardType.Number, Modifier.weight(1f))
                    SmallManualField(segment.recoveryPace, onRecoveryPaceChanged, stringResource(Res.string.field_manual_recovery_pace), KeyboardType.Text, Modifier.weight(1f))
                    SmallManualField(segment.recoveryInclinePercent, onRecoveryInclineChanged, stringResource(Res.string.field_manual_recovery_incline), KeyboardType.Decimal, Modifier.weight(1f))
                }
            }
        }
    }
}

@Preview
@Composable
private fun ManualSegmentCardPreview() {
    PreviewSurface {
        ManualSegmentCard(
            segment = ManualSegmentInput(
                id = "preview_manual_1",
                type = ManualSegmentType.REPETITION,
                durationMinutes = "5",
                pace = "5:05",
                inclinePercent = "7",
                repeats = "4",
                recoveryDurationMinutes = "3",
                recoveryPace = "6:05",
                recoveryInclinePercent = "3"
            ),
            onDurationChanged = {},
            onPaceChanged = {},
            onInclineChanged = {},
            onRepeatsChanged = {},
            onRecoveryDurationChanged = {},
            onRecoveryPaceChanged = {},
            onRecoveryInclineChanged = {},
            onRemove = {}
        )
    }
}
