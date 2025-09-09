package com.print.locky

import com.russhwolf.settings.Settings

expect fun getSettings(): Settings

object ThemeSettings {
    private const val KEY_DARK_MODE = "dark_mode"
    private val settings: Settings = getSettings()

    fun isDarkMode(): Boolean = settings.getBoolean(KEY_DARK_MODE, false)
    fun setDarkMode(value: Boolean) = settings.putBoolean(KEY_DARK_MODE, value)
}
