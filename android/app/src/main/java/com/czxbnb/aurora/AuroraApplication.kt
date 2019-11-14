package com.czxbnb.aurora

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

class AuroraApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }
    companion object {
        fun getApplicationContext() : Context {
            return getApplicationContext()
        }
    }
}