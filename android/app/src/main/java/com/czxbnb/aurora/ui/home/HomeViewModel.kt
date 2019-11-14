package com.czxbnb.aurora.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.czxbnb.aurora.base.BaseViewModel
import com.czxbnb.aurora.model.activity.Activity
import io.reactivex.disposables.Disposable
import javax.inject.Inject
import com.czxbnb.aurora.ERROR_TAG
import com.czxbnb.aurora.model.activity.ActivityCallback
import com.czxbnb.aurora.model.activity.ActivityRepository
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.ClassCastException


class HomeViewModel(
    val context: Context
) : BaseViewModel() {
    // Data Repository
    @Inject
    lateinit var activityRepository: ActivityRepository

    // Subscription
    private lateinit var activitySubscription: Disposable

    // Live data
    val activityLoadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val homeActivityAdapter: HomeActivityAdapter = HomeActivityAdapter()

    init {
        getActivityList()
    }

    private fun getActivityList() {
        // get the activity list
        activitySubscription = activityRepository.loadActivityList(context, object: ActivityCallback {
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

    private fun onLoadActivityListStart() {
        activityLoadingVisibility.value = View.VISIBLE
    }

    private fun onLoadActivityListFinish() {
        activityLoadingVisibility.value = View.GONE
    }

    @SuppressLint("SimpleDateFormat")
    private fun onLoadActivityListSuccess(activityList: List<Activity>) {
        homeActivityAdapter.updateActivityList(activityList)
    }

    private fun onLoadActivityListError(e: Throwable) {
        onErrorOccurred(e)
    }

    override fun onCleared() {
        super.onCleared()
        activitySubscription.dispose()
    }
}