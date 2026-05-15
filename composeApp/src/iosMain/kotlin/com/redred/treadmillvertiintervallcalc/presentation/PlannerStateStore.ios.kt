package com.redred.treadmillvertiintervallcalc.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.Foundation.NSUserDefaults

private const val PREFS_KEY = "planner_state_json"

private class IosPlannerStateStore : PlannerStateStore {
    private val defaults = NSUserDefaults.standardUserDefaults

    override fun load(): String? = defaults.stringForKey(PREFS_KEY)

    override fun save(value: String) {
        defaults.setObject(value, forKey = PREFS_KEY)
    }

    override fun clear() {
        defaults.removeObjectForKey(PREFS_KEY)
    }
}

@Composable
actual fun rememberPlannerStateStore(): PlannerStateStore = remember { IosPlannerStateStore() }
