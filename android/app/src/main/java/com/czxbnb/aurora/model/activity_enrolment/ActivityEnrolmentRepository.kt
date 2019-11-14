package com.czxbnb.aurora.model.activity_enrolment

import android.content.Context
import com.czxbnb.aurora.base.BaseRepository
import com.czxbnb.aurora.manager.SharedPreferenceManager
import com.czxbnb.aurora.network.ActivityApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ActivityEnrolmentRepository private constructor() : BaseRepository() {
    @Inject
    lateinit var activityApi: ActivityApi

    companion object {
        @Volatile
        private lateinit var instance: ActivityEnrolmentRepository

        fun getInstance(): ActivityEnrolmentRepository {
            if (!::instance.isInitialized) {
                synchronized(ActivityEnrolmentRepository::class.java) {
                    if (!::instance.isInitialized) {
                        instance = ActivityEnrolmentRepository()
                    }
                }
            }
            return instance
        }
    }

    fun enrollActivity(
        context: Context,
        activityId: String,
        activityEnrolmentCallback: ActivityEnrolmentCallback
    ): Disposable {
        return activityApi.enroll(
            SharedPreferenceManager.getInstance(context)?.token,
            "4", activityId
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { activityEnrolmentCallback.onEnrollActivityStart() }
            .doOnTerminate { activityEnrolmentCallback.onEnrollActivityFinish() }
            .subscribe(
                { result -> activityEnrolmentCallback.onEnrollActivitySuccess(result) },
                { error -> activityEnrolmentCallback.onEnrollActivityError(error) }
            )
    }
}