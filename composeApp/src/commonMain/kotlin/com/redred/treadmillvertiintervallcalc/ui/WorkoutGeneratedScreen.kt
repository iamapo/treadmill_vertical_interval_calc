package com.redred.treadmillvertiintervallcalc.ui

import com.redred.treadmillvertiintervallcalc.ui.components.*

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redred.treadmillvertiintervallcalc.model.WorkoutPlan
import org.jetbrains.compose.resources.stringResource
import verticaltreadmillrun.composeapp.generated.resources.Res
import verticaltreadmillrun.composeapp.generated.resources.button_copy_workout_text
import verticaltreadmillrun.composeapp.generated.resources.button_start_checklist
import verticaltreadmillrun.composeapp.generated.resources.vr_ic_copy
import verticaltreadmillrun.composeapp.generated.resources.vr_ic_play

@Composable
fun WorkoutGeneratedScreen(
    plan: WorkoutPlan,
    onOpenChecklist: () -> Unit,
    onCopyWorkoutText: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item { GeneratedHeroCard(plan) }
        item { GeneratedTimeline(plan) }
        item {
            Button(
                onClick = onOpenChecklist,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = VertiButtonShape,
                colors = ButtonDefaults.buttonColors(containerColor = VertiGreen, contentColor = Color.White)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    VrIcon(Res.drawable.vr_ic_play, contentDescription = null, size = 24.dp, tint = Color.White)
                    Text(stringResource(Res.string.button_start_checklist), fontWeight = FontWeight.Bold)
                }
            }
        }
        item {
            OutlinedButton(
                onClick = onCopyWorkoutText,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = VertiButtonShape
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    VrIcon(Res.drawable.vr_ic_copy, contentDescription = null, size = 22.dp, tint = VertiGreen)
                    Text(stringResource(Res.string.button_copy_workout_text), color = VertiGreen, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Preview
@Composable
private fun WorkoutGeneratedScreenPreview() {
    PreviewSurface {
        WorkoutGeneratedScreen(
            plan = PreviewWorkoutData.plan,
            onOpenChecklist = {},
            onCopyWorkoutText = {}
        )
    }
}
