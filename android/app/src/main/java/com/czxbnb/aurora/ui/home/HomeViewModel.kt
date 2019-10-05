package com.czxbnb.aurora.ui.home

import android.content.Context
import android.content.SharedPreferences
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
import javax.inject.Inject

class HomeViewModel(val context: Context) : BaseViewModel() {
    @Inject
    lateinit var activityApi: ActivityApi
    private lateinit var activitySubscription: Disposable
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val activityLoadingVisibility = MutableLiveData<Int>().apply { postValue(View.GONE) }
    val homeActivityAdapter: HomeActivityAdapter = HomeActivityAdapter()

    init {
        getActivityList()
    }

    fun getActivityList() {
        // get the activity list
        activitySubscription =
            activityApi.getActivities("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjoiY3p4Ym5iIiwiaWF0IjoxNTcwMjc1ODI0LCJleHAiOjE1NzA0NDg2MjQsImlzcyI6Imh0dHBzOi8vbG9jYWxob3N0OjMwMDAifQ.oMRJBMGC6tkjkslVIfrSPca2WAWVYw1Y1GC-efAnSrI")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onLoadActivityListStart() }
                .doOnTerminate { onLoadActivityListFinish() }
                .subscribe(
                    { result -> onLoadActivityListSuccess(result) },
                    { error -> onLoadActivityListError(error) }
                )
    }

    private fun onLoadActivityListStart() {
        activityLoadingVisibility.value = View.VISIBLE
    }

    private fun onLoadActivityListFinish() {
        activityLoadingVisibility.value = View.GONE
    }

    private fun onLoadActivityListSuccess(activityList: BaseData<List<Activity>>) {
        homeActivityAdapter.updateActivityList(activityList.data)
    }

    private fun onLoadActivityListError(error: Throwable) {
        errorMessage.value = error.message
    }

    override fun onCleared() {
        super.onCleared()
        activitySubscription.dispose()
    }
}