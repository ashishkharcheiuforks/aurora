package com.czxbnb.aurora

import android.app.Application
import androidx.multidex.MultiDex

class AuroraApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }
}