package com.realestate.app

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings


class SettingsProvider(private val context: Context) {
    fun createSettings(): Settings {
        val prefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        return SharedPreferencesSettings(prefs)
    }
}