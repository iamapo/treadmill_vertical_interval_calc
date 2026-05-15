package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redred.treadmillvertiintervallcalc.model.WorkoutPlan
import org.jetbrains.compose.resources.stringResource
import vertirun.composeapp.generated.resources.Res
import vertirun.composeapp.generated.resources.summary_total_distance
import vertirun.composeapp.generated.resources.summary_total_duration
import vertirun.composeapp.generated.resources.vr_ic_check_circle
import vertirun.composeapp.generated.resources.vr_ic_clock
import vertirun.composeapp.generated.resources.vr_ic_mountain
import vertirun.composeapp.generated.resources.vr_ic_route
import vertirun.composeapp.generated.resources.vr_ic_spark
import vertirun.composeapp.generated.resources.vr_ic_speedometer

@Composable
internal fun GeneratedHeroCard(plan: WorkoutPlan) {
    VertiCard {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(92.dp)
                    .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp))
                    .background(VertiGreenBrush),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        VrIcon(resource = Res.drawable.vr_ic_check_circle, contentDescription = null, size = 28.dp, tint = Color.White)
                        Text(
                            text = "Workout erstellt!",
                            modifier = Modifier.padding(horizontal = 10.dp),
                            color = Color.White,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        VrIcon(resource = Res.drawable.vr_ic_spark, contentDescription = null, size = 28.dp, tint = Color.White)
                    }
                    Text(
                        text = "Dein Plan ist bereit.",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 22.dp, horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                HeroMetric(Res.drawable.vr_ic_clock, stringResource(Res.string.summary_total_duration), plan.totalDurationMinutes.toString(), "min", Modifier.weight(1f))
                HeroMetric(Res.drawable.vr_ic_route, stringResource(Res.string.summary_total_distance), oneDecimalText(plan.totalDistanceKilometers), "km", Modifier.weight(1f))
                HeroMetric(Res.drawable.vr_ic_speedometer, "Ø Pace", paceText(plan.averagePaceMinutesPerKm), "min/km", Modifier.weight(1f))
                HeroMetric(Res.drawable.vr_ic_mountain, "Höhenmeter", metersText(plan.plannedElevationMeters).substringBefore(" "), "m", Modifier.weight(1f))
            }
        }
    }
}

@Preview
@Composable
private fun GeneratedHeroCardPreview() {
    PreviewSurface {
        GeneratedHeroCard(plan = PreviewWorkoutData.plan)
    }
}
