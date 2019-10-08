package com.czxbnb.aurora.ui.activity

import androidx.lifecycle.MutableLiveData
import com.czxbnb.aurora.base.BaseViewModel
import com.czxbnb.aurora.model.activity.Activity

class ActivityItemViewModel : BaseViewModel() {
    private val activityTitle = MutableLiveData<String>()
    private val activityDate = MutableLiveData<String>()
    private val activityImage = MutableLiveData<String>()

    fun bind(activity: Activity) {
        activityTitle.value = activity.title
        activityDate.value = activity.time
        activityImage.value = activity.image
    }

    fun getActivityTitle(): MutableLiveData<String> {
        return activityTitle
    }

    fun getActivityDate(): MutableLiveData<String> {
        return activityDate
    }

    fun getActivityImage(): MutableLiveData<String> {
        return activityImage
    }
}