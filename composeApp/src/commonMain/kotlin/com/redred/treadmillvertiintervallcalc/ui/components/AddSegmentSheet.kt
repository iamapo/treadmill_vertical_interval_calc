package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redred.treadmillvertiintervallcalc.presentation.ManualSegmentType
import org.jetbrains.compose.resources.stringResource
import vertirun.composeapp.generated.resources.Res
import vertirun.composeapp.generated.resources.button_add_segment
import vertirun.composeapp.generated.resources.field_manual_duration
import vertirun.composeapp.generated.resources.field_manual_incline_optional
import vertirun.composeapp.generated.resources.field_manual_pace
import vertirun.composeapp.generated.resources.field_manual_recovery_duration
import vertirun.composeapp.generated.resources.field_manual_recovery_incline
import vertirun.composeapp.generated.resources.field_manual_recovery_pace
import vertirun.composeapp.generated.resources.field_manual_repeats
import vertirun.composeapp.generated.resources.vr_ic_clock
import vertirun.composeapp.generated.resources.vr_ic_heart_recovery
import vertirun.composeapp.generated.resources.vr_ic_incline_up
import vertirun.composeapp.generated.resources.vr_ic_repeat
import vertirun.composeapp.generated.resources.vr_ic_spark
import vertirun.composeapp.generated.resources.vr_ic_speedometer

@Composable
internal fun AddSegmentSheet(
    modifier: Modifier = Modifier,
    onAddManualSegment: (ManualSegmentType, String, String, String, String, String, String, String) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedType by remember { mutableStateOf<ManualSegmentType?>(null) }
    var step by remember { mutableStateOf(1) }
    var durationMinutes by remember { mutableStateOf("5") }
    var pace by remember { mutableStateOf("5:10") }
    var inclinePercent by remember { mutableStateOf("7") }
    var repeats by remember { mutableStateOf("4") }
    var recoveryDurationMinutes by remember { mutableStateOf("3") }
    var recoveryPace by remember { mutableStateOf("6:00") }
    var recoveryInclinePercent by remember { mutableStateOf("3") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(Res.string.button_add_segment),
            color = VertiText,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Schritt $step von 2",
            color = if (step == 1) VertiGreen else VertiMuted,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        if (step == 1) {
            ManualSegmentType.entries.forEach { type ->
                SegmentTypeChoice(
                    type = type,
                    selected = selectedType == type,
                    onClick = {
                        selectedType = type
                        durationMinutes = if (type == ManualSegmentType.COOLDOWN) "8" else if (type == ManualSegmentType.SINGLE) "12" else "5"
                        pace = if (type == ManualSegmentType.COOLDOWN) "6:30" else if (type == ManualSegmentType.WARMUP) "6:20" else "5:10"
                        inclinePercent = if (type == ManualSegmentType.COOLDOWN) "1" else if (type == ManualSegmentType.WARMUP) "2" else "7"
                    }
                )
            }
        } else {
            SheetField(Res.drawable.vr_ic_clock, stringResource(Res.string.field_manual_duration), durationMinutes, { durationMinutes = it }, KeyboardType.Number)
            SheetField(Res.drawable.vr_ic_speedometer, stringResource(Res.string.field_manual_pace), pace, { pace = it }, KeyboardType.Text)
            SheetField(Res.drawable.vr_ic_incline_up, stringResource(Res.string.field_manual_incline_optional), inclinePercent, { inclinePercent = it }, KeyboardType.Decimal)
            if (selectedType == ManualSegmentType.REPETITION) {
                SheetField(Res.drawable.vr_ic_repeat, stringResource(Res.string.field_manual_repeats), repeats, { repeats = it }, KeyboardType.Number)
                SheetField(Res.drawable.vr_ic_heart_recovery, stringResource(Res.string.field_manual_recovery_duration), recoveryDurationMinutes, { recoveryDurationMinutes = it }, KeyboardType.Number)
                SheetField(Res.drawable.vr_ic_speedometer, stringResource(Res.string.field_manual_recovery_pace), recoveryPace, { recoveryPace = it }, KeyboardType.Text)
                SheetField(Res.drawable.vr_ic_incline_up, stringResource(Res.string.field_manual_recovery_incline), recoveryInclinePercent, { recoveryInclinePercent = it }, KeyboardType.Decimal)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = {
                    if (step == 1) onDismiss() else step = 1
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(if (step == 1) "Abbrechen" else "Zurück", color = VertiGreen, fontWeight = FontWeight.Bold)
            }
            Button(
                onClick = {
                    if (step == 1) {
                        if (selectedType != null) step = 2
                    } else {
                        onAddManualSegment(
                            selectedType ?: return@Button,
                            durationMinutes,
                            pace,
                            inclinePercent,
                            repeats,
                            recoveryDurationMinutes,
                            recoveryPace,
                            recoveryInclinePercent
                        )
                    }
                },
                modifier = Modifier
                    .weight(1.5f)
                    .height(58.dp),
                enabled = step == 2 || selectedType != null,
                shape = VertiButtonShape,
                colors = ButtonDefaults.buttonColors(containerColor = VertiGreen, contentColor = Color.White)
            ) {
                if (step == 1) {
                    Text("Weiter", fontWeight = FontWeight.Bold)
                } else {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        VrIcon(
                            resource = Res.drawable.vr_ic_spark,
                            contentDescription = null,
                            size = 22.dp,
                            tint = Color.White
                        )
                        Text(stringResource(Res.string.button_add_segment), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        Spacer(Modifier.height(8.dp))
    }
}

@Preview
@Composable
private fun AddSegmentSheetPreview() {
    PreviewSurface {
        AddSegmentSheet(
            onAddManualSegment = { _, _, _, _, _, _, _, _ -> },
            onDismiss = {}
        )
    }
}
