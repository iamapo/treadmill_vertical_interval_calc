package com.redred.treadmillvertiintervallcalc.presentation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

private const val PREFS_FILE = "planner_state_store"
private const val PREFS_KEY = "planner_state_json"

private class AndroidPlannerStateStore(
    context: Context
) : PlannerStateStore {
    private val prefs = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)

    override fun load(): String? = prefs.getString(PREFS_KEY, null)

    override fun save(value: String) {
        prefs.edit().putString(PREFS_KEY, value).apply()
    }

    override fun clear() {
        prefs.edit().remove(PREFS_KEY).apply()
    }
}

@Composable
actual fun rememberPlannerStateStore(): PlannerStateStore {
    val context = LocalContext.current.applicationContext
    return remember(context) { AndroidPlannerStateStore(context) }
}
