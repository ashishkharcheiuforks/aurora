package com.czxbnb.aurora.base

import com.czxbnb.aurora.injection.*
import com.czxbnb.aurora.injection.component.DaggerRepositoryComponent
import com.czxbnb.aurora.injection.component.RepositoryComponent
import com.czxbnb.aurora.injection.module.ApiModule
import com.czxbnb.aurora.model.activity.ActivityRepository
import com.czxbnb.aurora.model.activity_enrolment.ActivityEnrolmentRepository
import com.czxbnb.aurora.model.user.UserRepository

abstract class BaseRepository () {
    private val component: RepositoryComponent = DaggerRepositoryComponent
        .builder()
        .apiModule(ApiModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when(this) {
            is UserRepository -> component.inject(this)
            is ActivityRepository -> component.inject(this)
            is ActivityEnrolmentRepository -> component.inject(this)
        }
    }
}