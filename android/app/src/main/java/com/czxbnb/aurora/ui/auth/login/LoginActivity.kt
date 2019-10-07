package com.czxbnb.aurora.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.czxbnb.aurora.ui.main.MainActivity
import com.czxbnb.aurora.R
import com.czxbnb.aurora.base.BaseActivity
import com.czxbnb.aurora.databinding.ActivityLoginBinding
import com.czxbnb.aurora.injection.ViewModelFactory
import com.czxbnb.aurora.manager.SharedPreferenceManager
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Bind view model
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(LoginViewModel::class.java)
        binding.viewModel = viewModel

        // Observe error message
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
