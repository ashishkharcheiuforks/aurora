package com.czxbnb.aurora.ui.activity

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.czxbnb.aurora.AuroraApplication
import com.czxbnb.aurora.base.BaseViewModel
import com.czxbnb.aurora.model.activity.Activity
import com.czxbnb.aurora.model.activity.ActivityCallback
import com.czxbnb.aurora.model.activity.ActivityRepository
import com.czxbnb.aurora.ui.error.NoInternetFragment
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class ActivityViewModel: BaseViewModel() {
    // Data Repository
    @Inject
    lateinit var activityRepository: ActivityRepository

    // Subscriptions
    private lateinit var activitySubscription: Disposable
    private lateinit var activityRefreshSubscription: Disposable

    // Live data
    val activityLoadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val activityRefreshVisibility: MutableLiveData<Boolean> = MutableLiveData()

    // Recycler view adapter
    val activityAdapter: ActivityAdapter = ActivityAdapter()

    init {
        getActivityList()
    }

    fun getActivityList() {
        activitySubscription = activityRepository.loadActivityList(AuroraApplication.getApplicationContext(),
            object : ActivityCallback {
            override fun onLoadActivityStart() {
                onLoadActivityListStart()
            }

            override fun onLoadActivityFinish() {
               onLoadActivityListFinish()
            }

            override fun onLoadActivitySuccess(activityList: List<Activity>) {
                onLoadActivityListSuccess(activityList)
            }

            override fun onLoadActivityError(e: Throwable) {
                onLoadActivityListError(e)
            }
        })
    }

    fun refreshActivityListFromRemoteSource() {
        activityRefreshSubscription = activityRepository.loadActivityListFromRemoteArea(AuroraApplication.getApplicationContext(),
            object : ActivityCallback {
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
        onErrorOccurred(e)
    }

    override fun onCleared() {
        super.onCleared()
        activitySubscription.dispose()
        activityRefreshSubscription.dispose()
    }
}