package com.czxbnb.aurora.base

import androidx.lifecycle.ViewModel
import com.czxbnb.aurora.injection.DaggerViewModelInjector
import com.czxbnb.aurora.injection.ViewModelInjector
import com.czxbnb.aurora.injection.NetworkInjector
import com.czxbnb.aurora.ui.auth.login.LoginViewModel
import com.czxbnb.aurora.ui.home.HomeActivityViewModel
import com.czxbnb.aurora.ui.home.HomeViewModel
import com.czxbnb.aurora.ui.main.MainViewModel
import com.czxbnb.aurora.ui.post.PostListViewModel

abstract class BaseViewModel : ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkInjector)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when(this) {
            is PostListViewModel -> injector.inject(this)
            is LoginViewModel -> injector.inject(this)
            is MainViewModel -> injector.inject(this)
            is HomeViewModel -> injector.inject(this)
            is HomeActivityViewModel -> injector.inject(this)
        }
    }
}