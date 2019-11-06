package com.czxbnb.aurora.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.czxbnb.aurora.base.BaseData
import com.czxbnb.aurora.base.BaseViewModel
import com.czxbnb.aurora.manager.SharedPreferenceManager
import com.czxbnb.aurora.model.activity.Activity
import com.czxbnb.aurora.network.ActivityApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.czxbnb.aurora.model.activity.ActivityDao
import io.reactivex.Observable
import java.util.*
import retrofit2.adapter.rxjava2.Result.response
import android.R.string
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import com.czxbnb.aurora.ERROR_TAG
import com.czxbnb.aurora.model.activity.ActivityCallback
import com.czxbnb.aurora.model.activity.ActivityRepository
import com.google.gson.JsonParseException
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.ClassCastException


class HomeViewModel(
    val context: Context,
    private val activityDao: ActivityDao
) : BaseViewModel() {
    // Data Repository
    @Inject
    lateinit var activityRepository: ActivityRepository

    // Activity api
    @Inject
    lateinit var activityApi: ActivityApi

    // Subscription
    private lateinit var activitySubscription: Disposable

    // Live data
    val errorMessage: MutableLiveData<String> = MutableLiveData()
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
        try {
            val errorBody = JSONObject((e as HttpException).response().errorBody()!!.string())
            errorMessage.value = errorBody.getString(ERROR_TAG)
        } catch (ex: ClassCastException) {
            errorMessage.value = e.message
        }
    }

    override fun onCleared() {
        super.onCleared()
        activitySubscription.dispose()
    }
}