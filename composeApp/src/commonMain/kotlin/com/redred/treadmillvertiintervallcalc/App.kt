package com.redred.treadmillvertiintervallcalc

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.redred.treadmillvertiintervallcalc.presentation.WorkoutPlannerViewModel
import com.redred.treadmillvertiintervallcalc.ui.WorkoutPlannerScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        val viewModel = remember { WorkoutPlannerViewModel() }
        WorkoutPlannerScreen(viewModel = viewModel)
    }
}
