package com.czxbnb.aurora.ui.login

import android.os.Bundle
import android.view.View
import com.czxbnb.aurora.R
import com.czxbnb.aurora.base.BaseActivity
import com.czxbnb.aurora.databinding.ActivityLoginBinding
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
