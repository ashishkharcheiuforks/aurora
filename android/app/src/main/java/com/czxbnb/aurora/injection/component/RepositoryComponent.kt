package com.czxbnb.aurora.injection.component

import com.czxbnb.aurora.injection.module.ApiModule
import com.czxbnb.aurora.model.activity.ActivityRepository
import com.czxbnb.aurora.model.activity_enrolment.ActivityEnrolmentRepository
import com.czxbnb.aurora.model.news.NewsRepository
import com.czxbnb.aurora.model.user.UserRepository
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

    /**
     * Injects required dependencies into the specified activityEnrolmentRepository
     * @param activityEnrolmentRepository activityEnrolmentRepository in which to inject the dependencies
     */
    fun inject(activityEnrolmentRepository: ActivityEnrolmentRepository)

    /**
     * Injects required dependencies into the specified userRepository
     * @param userRepository user repository in which to inject the dependencies
     */
    fun inject(userRepository: UserRepository)


    /**
     * Injects required dependencies into the specified newsRepository
     * @param newsRepository news repository in which to inject the dependencies
     */
    fun inject(newsRepository: NewsRepository)
}