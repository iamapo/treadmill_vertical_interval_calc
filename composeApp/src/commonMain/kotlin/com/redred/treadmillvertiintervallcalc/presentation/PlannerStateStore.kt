package com.redred.treadmillvertiintervallcalc.presentation

import androidx.compose.runtime.Composable

interface PlannerStateStore {
    fun load(): String?
    fun save(value: String)
    fun clear()
}

@Composable
expect fun rememberPlannerStateStore(): PlannerStateStore
