package com.czxbnb.aurora

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex


class AuroraApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        MultiDex.install(this)
    }
    companion object {
        private lateinit var context: Context
        fun getApplicationContext() : Context {
            return context
        }
    }
}