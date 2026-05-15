package com.redred.treadmillvertiintervallcalc

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.redred.treadmillvertiintervallcalc.presentation.rememberPlannerStateStore
import com.redred.treadmillvertiintervallcalc.presentation.WorkoutPlannerViewModel
import com.redred.treadmillvertiintervallcalc.ui.components.VertiGreen
import com.redred.treadmillvertiintervallcalc.ui.components.VertiGreenSoft
import com.redred.treadmillvertiintervallcalc.ui.components.VertiPage
import com.redred.treadmillvertiintervallcalc.ui.components.VertiText
import com.redred.treadmillvertiintervallcalc.ui.WorkoutPlannerScreen

@Composable
@Preview
fun App() {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = VertiGreen,
            primaryContainer = VertiGreenSoft,
            secondary = VertiGreen,
            background = VertiPage,
            surface = androidx.compose.ui.graphics.Color.White,
            onPrimary = androidx.compose.ui.graphics.Color.White,
            onPrimaryContainer = VertiText,
            onBackground = VertiText,
            onSurface = VertiText
        )
    ) {
        val stateStore = rememberPlannerStateStore()
        val viewModel = remember { WorkoutPlannerViewModel(plannerStateStore = stateStore) }
        WorkoutPlannerScreen(viewModel = viewModel)
    }
}
