package com.czxbnb.aurora.injection.component

import com.czxbnb.aurora.injection.module.ApiModule
import com.czxbnb.aurora.model.activity.ActivityRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApiModule::class)])
interface RepositoryComponent {
    @Component.Builder
    interface Builder {
        fun build() : RepositoryComponent

        fun apiModule(apiModule: ApiModule): Builder
    }

    /**
     * Injects required dependencies into the specified activityRepository
     * @param activityRepository activityRepository in which to inject the dependencies
     */
    fun inject(activityRepository: ActivityRepository)
}