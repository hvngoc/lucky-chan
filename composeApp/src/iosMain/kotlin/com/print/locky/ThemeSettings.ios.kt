package com.print.locky

import com.russhwolf.settings.Settings
import com.russhwolf.settings.NSUserDefaultsSettings

actual fun getSettings(): Settings = NSUserDefaultsSettings.Factory().create("locky")

