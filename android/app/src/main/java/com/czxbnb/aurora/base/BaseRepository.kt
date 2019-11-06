package com.czxbnb.aurora.base

import com.czxbnb.aurora.injection.*
import com.czxbnb.aurora.injection.component.RepositoryComponent
import com.czxbnb.aurora.injection.module.ApiModule
import com.czxbnb.aurora.model.activity.ActivityRepository

abstract class BaseRepository () {
    private val component: RepositoryComponent = DaggerRepositoryComponent
        .builder()
        .networkModule(ApiModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when(this) {
            is ActivityRepository -> component.inject(this)
        }
    }
}