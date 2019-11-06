package com.czxbnb.aurora.ui.activity

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.czxbnb.aurora.ERROR_TAG
import com.czxbnb.aurora.base.BaseViewModel
import com.czxbnb.aurora.manager.SharedPreferenceManager
import com.czxbnb.aurora.model.activity.Activity
import com.czxbnb.aurora.model.activity.ActivityCallback
import com.czxbnb.aurora.model.activity.ActivityDao
import com.czxbnb.aurora.model.activity.ActivityRepository
import com.czxbnb.aurora.network.ActivityApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.ClassCastException
import javax.inject.Inject

class ActivityViewModel(
    val context: Context,
    private val activityDao: ActivityDao
) : BaseViewModel() {
    // Data Repository
    @Inject
    lateinit var activityRepository: ActivityRepository

    @Inject
    lateinit var activityApi: ActivityApi

    // Subscriptions
    private lateinit var activitySubscription: Disposable
    private lateinit var activityRefreshSubscription: Disposable

    // Live data
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val activityLoadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val activityRefreshVisibility: MutableLiveData<Boolean> = MutableLiveData()
    val activityAdapter: ActivityAdapter = ActivityAdapter()

    init {
        getActivityList()
    }

    private fun getActivityList() {
        activitySubscription = Observable.fromCallable { activityDao.all }
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
            .doOnSubscribe { onLoadActivityListStart() }
            .doOnTerminate { onLoadActivityListFinish() }
            .subscribe(
                { result -> onLoadActivityListSuccess(result) },
                { error -> onLoadActivityListError(error) }
            )
    }

    fun refreshActivityListFromRemoteSource() {
        activityRefreshSubscription = activityRepository.loadActivityListFromRemoteArea(context, object : ActivityCallback {
            override fun onLoadActivityStart() {
                onRefreshActivityListStart()
            }

            override fun onLoadActivityFinish() {
                onRefreshActivityListFinish()
            }

            override fun onLoadActivitySuccess(activityList: List<Activity>) {
                onLoadActivityListSuccess(activityList)
            }

            override fun onLoadActivityError(e: Throwable) {
                onLoadActivityListError(e)
            }
        })
    }

    private fun onLoadActivityListStart() {
        activityLoadingVisibility.value = View.VISIBLE
    }

    private fun onLoadActivityListFinish() {
        activityLoadingVisibility.value = View.GONE
    }

    private fun onRefreshActivityListStart() {
        activityRefreshVisibility.value = true
    }

    private fun onRefreshActivityListFinish() {
        activityRefreshVisibility.value = false
    }

    private fun onLoadActivityListSuccess(activityList: List<Activity>) {
        activityAdapter.updateActivityList(activityList)
    }

    private fun onLoadActivityListError(e: Throwable) {
        try {
            val errorBody = JSONObject((e as HttpException).response().errorBody()!!.string())
            errorMessage.value = errorBody.getString(ERROR_TAG)
        } catch (exception: ClassCastException) {
            exception.printStackTrace()
        } finally {
            errorMessage.value = e.message
        }
    }

    override fun onCleared() {
        super.onCleared()
        activitySubscription.dispose()
        activityRefreshSubscription.dispose()
    }
}