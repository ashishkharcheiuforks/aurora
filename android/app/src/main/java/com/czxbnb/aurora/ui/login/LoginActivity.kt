package com.czxbnb.aurora.ui.auth.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
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

class LoginActivity :
    BaseActivity<LoginViewModel, ActivityLoginBinding>(LoginViewModel::class.java) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.viewModel = viewModel
    }

    fun loginClick(view: View) {
        val username = et_username.text.toString()
        val password = et_password.text.toString()

        viewModel.login(username, password)
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_login
    }
}
