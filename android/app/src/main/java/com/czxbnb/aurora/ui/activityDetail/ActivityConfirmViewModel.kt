package com.czxbnb.aurora.ui.activityDetail

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.czxbnb.aurora.base.BaseViewModel
import com.czxbnb.aurora.model.activity.Activity

class ActivityConfirmViewModel (val context: Context) : BaseViewModel() {
    private val activity : MutableLiveData<Activity> = MutableLiveData()

    fun loadActivity(activity : Activity) {
        this.activity.value = activity
    }

    fun getActivity(): MutableLiveData<Activity> {
        return activity
    }
}