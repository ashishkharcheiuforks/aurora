package com.czxbnb.aurora.base

import androidx.lifecycle.ViewModel
import com.czxbnb.aurora.injection.component.DaggerViewModelInjector
import com.czxbnb.aurora.injection.component.ViewModelInjector
import com.czxbnb.aurora.injection.module.NetworkModule
import com.czxbnb.aurora.viewmodel.PostListViewModel

abstract class BaseViewModel : ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when(this) {
            is PostListViewModel -> injector.inject(this)
        }
    }
}