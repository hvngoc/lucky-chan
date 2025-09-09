package com.print.locky

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

private var appContext: Context? = null

fun initThemeSettings(context: Context) {
    appContext = context.applicationContext
}

actual fun getSettings(): Settings {
    return SharedPreferencesSettings.Factory(appContext!!).create("locky")
}

