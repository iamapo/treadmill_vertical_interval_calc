package com.redred.treadmillvertiintervallcalc.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.util.prefs.Preferences

private const val PREFS_KEY = "planner_state_json"

private class DesktopPlannerStateStore : PlannerStateStore {
    private val prefs = Preferences.userRoot().node("com.redred.treadmillvertiintervallcalc")

    override fun load(): String? = prefs.get(PREFS_KEY, null)

    override fun save(value: String) {
        prefs.put(PREFS_KEY, value)
    }

    override fun clear() {
        prefs.remove(PREFS_KEY)
    }
}

@Composable
actual fun rememberPlannerStateStore(): PlannerStateStore = remember { DesktopPlannerStateStore() }
