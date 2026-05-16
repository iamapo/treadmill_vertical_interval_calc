package com.redred.treadmillvertiintervallcalc.ui.components

import com.redred.treadmillvertiintervallcalc.ui.*

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redred.treadmillvertiintervallcalc.presentation.WorkoutInputField
import com.redred.treadmillvertiintervallcalc.presentation.WorkoutPlannerState
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import verticaltreadmillrun.composeapp.generated.resources.Res
import verticaltreadmillrun.composeapp.generated.resources.field_target_elevation
import verticaltreadmillrun.composeapp.generated.resources.section_workout_target
import verticaltreadmillrun.composeapp.generated.resources.vr_ic_mountain
import verticaltreadmillrun.composeapp.generated.resources.vr_ic_target

@Composable
internal fun AutoSectionCard(
    number: Int,
    title: StringResource,
    icon: DrawableResource,
    content: @Composable ColumnScope.() -> Unit
) {
    VertiCard {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                SectionNumber(number)
                Text(
                    text = stringResource(title),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp),
                    color = VertiText,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                VrIcon(resource = icon, contentDescription = null, size = 26.dp, tint = VertiGreen)
            }
            content()
        }
    }
}

@Preview
@Composable
private fun AutoSectionCardPreview() {
    PreviewSurface {
        AutoSectionCard(number = 1, title = Res.string.section_workout_target, icon = Res.drawable.vr_ic_target) {
            InputRow(
                icon = Res.drawable.vr_ic_mountain,
                label = Res.string.field_target_elevation,
                state = WorkoutPlannerState(targetElevation = "600"),
                field = WorkoutInputField.TARGET_ELEVATION,
                placeholder = "600 m",
                onInputChanged = { _, _ -> }
            )
        }
    }
}
