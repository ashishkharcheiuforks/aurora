package com.czxbnb.aurora.ui.auth.login

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.czxbnb.aurora.MainActivity
import com.czxbnb.aurora.R
import com.czxbnb.aurora.databinding.ActivityLoginBinding
import com.czxbnb.aurora.injection.ViewModelFactory
import com.czxbnb.aurora.manager.SharedPreferenceManager
import com.google.android.material.snackbar.Snackbar
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // If token exist, redirect to main activity
        if (SharedPreferenceManager.getInstance(this)?.token != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        ImmersionBar.with(this).init()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(LoginViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) {
                showError(errorMessage)
            }
        })
    }

    fun loginClick(view: View) {
        val username = et_username.text.toString()
        val password = et_password.text.toString()

        viewModel.login(username, password)
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }
}
