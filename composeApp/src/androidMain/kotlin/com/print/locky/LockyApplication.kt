package com.print.locky

import androidx.multidex.MultiDexApplication

class LockyApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        initThemeSettings(this)
        // Initialize any application-level components here
    }
}
