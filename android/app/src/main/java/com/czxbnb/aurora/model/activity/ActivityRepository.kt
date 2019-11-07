package com.czxbnb.aurora.model.activity

import android.annotation.SuppressLint
import android.content.Context
import com.czxbnb.aurora.base.BaseRepository
import com.czxbnb.aurora.manager.SharedPreferenceManager
import com.czxbnb.aurora.model.AppDatabase
import com.czxbnb.aurora.network.ActivityApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ActivityRepository private constructor() : BaseRepository() {
    @Inject
    lateinit var activityApi: ActivityApi

    companion object {
        @Volatile
        private lateinit var instance: ActivityRepository

        fun getInstance(): ActivityRepository {
            if (!::instance.isInitialized) {
                synchronized(ActivityRepository::class.java) {
                    if (!::instance.isInitialized) {
                        instance = ActivityRepository()
                    }
                }
            }
            return instance
        }
    }

    fun loadActivityList(
        context: Context,
        activityCallback: ActivityCallback
    ): Disposable {
        val activityDao = AppDatabase.getInstance(context)!!.activityDao()
        return Observable.fromCallable { activityDao.all }
            .concatMap { dbActivityList ->
                if (dbActivityList.isEmpty()) {
                    activityApi.getActivities(SharedPreferenceManager.getInstance(context)?.token)
                        .concatMap { apiActivityList ->
                            activityDao.insertAll(*apiActivityList.data.toTypedArray())
                            Observable.just(apiActivityList.data)
                        }

                } else {
                    Observable.just(dbActivityList)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { activityCallback.onLoadActivityStart() }
            .doOnTerminate { activityCallback.onLoadActivityFinish() }
            .subscribe(
                { result -> activityCallback.onLoadActivitySuccess(result) },
                { error -> activityCallback.onLoadActivityError(error) }
            )
    }

    fun loadActivityListFromRemoteArea(
        context: Context,
        activityCallback: ActivityCallback
    ): Disposable {
        return activityApi.getActivities(SharedPreferenceManager.getInstance(context)?.token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { activityCallback.onLoadActivityStart() }
            .doOnTerminate { activityCallback.onLoadActivityFinish() }
            .subscribe(
                { result -> activityCallback.onLoadActivitySuccess(result.data) },
                { error -> activityCallback.onLoadActivityError(error) }
            )
    }
}