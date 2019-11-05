package com.czxbnb.aurora.base

import com.czxbnb.aurora.injection.*
import com.czxbnb.aurora.model.activity.ActivityRepository

abstract class BaseRepository () {
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkInjector)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when(this) {
            is ActivityRepository -> injector.inject(this)
        }
    }
}