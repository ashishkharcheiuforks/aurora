package com.czxbnb.aurora.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.czxbnb.aurora.injection.component.DaggerViewModelComponent
import com.czxbnb.aurora.injection.component.ViewModelComponent
import com.czxbnb.aurora.injection.module.ApiModule
import com.czxbnb.aurora.injection.module.RepositoryModule
import com.czxbnb.aurora.ui.activity.ActivityViewModel
import com.czxbnb.aurora.ui.activityDetail.ActivityConfirmViewModel
import com.czxbnb.aurora.ui.activityDetail.ActivityDetailViewModel
import com.czxbnb.aurora.ui.auth.login.LoginViewModel
import com.czxbnb.aurora.ui.home.HomeActivityViewModel
import com.czxbnb.aurora.ui.home.HomeViewModel
import com.czxbnb.aurora.ui.main.MainViewModel
import com.czxbnb.aurora.ui.post.PostListViewModel

abstract class BaseViewModel : ViewModel() {
    private val component: ViewModelComponent = DaggerViewModelComponent
        .builder()
        .apiModule(ApiModule)
        .repositoryModule(RepositoryModule)
        .build()

    val errorMessage: MutableLiveData<String> = MutableLiveData()

    init {
        inject()
    }

    private fun inject() {
        when(this) {
            is PostListViewModel -> component.inject(this)
            is LoginViewModel -> component.inject(this)
            is MainViewModel -> component.inject(this)
            is HomeViewModel -> component.inject(this)
            is HomeActivityViewModel -> component.inject(this)
            is ActivityViewModel -> component.inject(this)
            is ActivityDetailViewModel -> component.inject(this)
            is ActivityConfirmViewModel -> component.inject(this)
        }
    }
}