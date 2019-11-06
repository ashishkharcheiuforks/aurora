package com.czxbnb.aurora.injection.module

import com.czxbnb.aurora.model.AppDatabase
import com.czxbnb.aurora.model.activity.ActivityDao
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
@Suppress("unused")
object DaoModule {
    /**
     * Provides the Activity Data Access Object Interface
     * @param appDatabase the AppDatabase object used to instantiate the dao
     * @return the Activity Dao implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideActivityDao(appDatabase: AppDatabase): ActivityDao {
        return appDatabase.activityDao()
    }
}