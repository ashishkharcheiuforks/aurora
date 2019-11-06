package com.czxbnb.aurora.injection.module

import com.czxbnb.aurora.model.activity.ActivityRepository
import com.czxbnb.aurora.model.activity_enrolment.ActivityEnrolmentRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
@Suppress("unused")
object RepositoryModule {
    /**
     * Provides the Activity repository implementation.
     * @return the Activity repository implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideActivityRepository(): ActivityRepository {
        return ActivityRepository.getInstance()
    }

    /**
     * Provides the Activity enrolment repository implementation.
     * @return the Activity enrolment repository implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideActivityEnrolmentRepository(): ActivityEnrolmentRepository {
        return ActivityEnrolmentRepository.getInstance()
    }
}