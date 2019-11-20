package com.czxbnb.aurora.ui.activityDetail

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.czxbnb.aurora.base.BaseViewModel
import com.czxbnb.aurora.model.activity.Activity
import com.czxbnb.aurora.network.api.ActivityApi
import javax.inject.Inject

class ActivityDetailViewModel(val context: Context) : BaseViewModel() {
    @Inject
    lateinit var activityApi: ActivityApi
    // private lateinit var subscription: Disposable
    private val activity : MutableLiveData<Activity> = MutableLiveData()

    fun loadActivity(activity : Activity) {
        this.activity.value = activity
    }

    fun getActivity(): MutableLiveData<Activity> {
        return activity
    }

    override fun onCleared() {
        super.onCleared()
    }
}