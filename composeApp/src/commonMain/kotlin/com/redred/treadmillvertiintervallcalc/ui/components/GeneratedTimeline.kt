package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redred.treadmillvertiintervallcalc.model.WorkoutPlan
import vertirun.composeapp.generated.resources.Res
import vertirun.composeapp.generated.resources.vr_ic_chevron_right
import vertirun.composeapp.generated.resources.vr_ic_clock
import vertirun.composeapp.generated.resources.vr_ic_incline_up
import vertirun.composeapp.generated.resources.vr_ic_mountain
import vertirun.composeapp.generated.resources.vr_ic_speedometer

@Composable
internal fun GeneratedTimeline(plan: WorkoutPlan) {
    VertiCard {
        Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp)) {
            plan.segments.forEachIndexed { index, segment ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(10.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, VertiLine),
                    shadowElevation = 1.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconBubble(segmentIconResource(segment.type), modifier = Modifier.padding(end = 12.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(segment.title)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                PlanMetric(Res.drawable.vr_ic_clock, "${segment.durationMinutes} min")
                                PlanMetric(Res.drawable.vr_ic_speedometer, "${segment.pace} min/km")
                                PlanMetric(Res.drawable.vr_ic_incline_up, inclineText(segment.inclinePercent))
                                PlanMetric(Res.drawable.vr_ic_mountain, metersText(segment.elevationMeters).replace(" ", " "))
                            }
                        }
                    }
                }
                if (index != plan.segments.lastIndex) {
                    Spacer(modifier = Modifier.height(2.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun GeneratedTimelinePreview() {
    PreviewSurface {
        GeneratedTimeline(plan = PreviewWorkoutData.plan)
    }
}
