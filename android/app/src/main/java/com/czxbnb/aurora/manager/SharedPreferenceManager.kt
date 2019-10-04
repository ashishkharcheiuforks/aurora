package com.czxbnb.aurora.manager

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceManager private constructor(private val context: Context) {
    private val mPrefs: SharedPreferences

    var token: String?
        get() = mPrefs.getString(KEY_TOKEN, null)
        set(token) = mPrefs.edit().putString(KEY_TOKEN, token).apply()

    init {
        mPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    companion object {
        private const val PREF_NAME = "pref_aurora"
        private const val KEY_TOKEN = "key_token"
        @SuppressLint("StaticFieldLeak")
        private var instance: SharedPreferenceManager? = null

        fun getInstance(context: Context): SharedPreferenceManager? {
            if (instance == null) {
                synchronized(SharedPreferenceManager::class.java) {
                    if (instance == null) {
                        instance = SharedPreferenceManager(context)
                    }
                }
            }
            return instance
        }
    }
}
