package com.czxbnb.aurora.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.czxbnb.aurora.ui.main.MainActivity
import com.czxbnb.aurora.R
import com.czxbnb.aurora.manager.SharedPreferenceManager
import com.czxbnb.aurora.ui.login.LoginActivity

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        jumpToAppropriatePage()
    }

    private fun jumpToAppropriatePage() {
        // If no token found, jump to login activity
        // Otherwise, jump to main page
        val intent: Intent = if (SharedPreferenceManager.getInstance(this)?.token == null) {
            Intent(this, LoginActivity::class.java)
        } else {
            Intent(this, MainActivity::class.java)
        }
        startActivity(intent)
    }
}
