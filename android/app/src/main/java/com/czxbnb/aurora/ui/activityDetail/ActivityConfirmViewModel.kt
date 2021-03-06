package com.czxbnb.aurora.ui.activityDetail

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.czxbnb.aurora.AuroraApplication
import com.czxbnb.aurora.base.BaseViewModel
import com.czxbnb.aurora.model.activity.Activity
import com.czxbnb.aurora.model.activity_enrolment.ActivityEnrolment
import com.czxbnb.aurora.model.activity_enrolment.ActivityEnrolmentCallback
import com.czxbnb.aurora.model.activity_enrolment.ActivityEnrolmentRepository
import com.czxbnb.aurora.network.api.ActivityApi
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class ActivityConfirmViewModel: BaseViewModel() {
    @Inject
    lateinit var activityApi: ActivityApi

    @Inject
    lateinit var activityEnrolmentRepository: ActivityEnrolmentRepository

    private val activity: MutableLiveData<Activity> = MutableLiveData()
    private lateinit var enrollSubscription: Disposable
    val progress: MutableLiveData<Int> = MutableLiveData()


    fun loadActivity(activity: Activity) {
        this.activity.value = activity
    }

    fun getActivity(): MutableLiveData<Activity> {
        return activity
    }

    fun enrollActivity(activityId: String) {
        enrollSubscription = activityEnrolmentRepository.enrollActivity(AuroraApplication.getApplicationContext(),
            activityId, object : ActivityEnrolmentCallback {
            override fun onEnrollActivityStart() {
                onViewModelEnrollActivityStart()
            }

            override fun onEnrollActivityFinish() {
              onViewModelEnrollActivityFinish()
            }

            override fun onEnrollActivitySuccess(activityEnrolment: ActivityEnrolment) {
                onViewModelEnrollActivitySuccess(activityEnrolment)
            }

            override fun onEnrollActivityError(e: Throwable) {
               onViewModelEnrollActivityError(e)
            }
        })
    }

    private fun onViewModelEnrollActivityStart() {
        progress.value = 1
    }

    private fun onViewModelEnrollActivityFinish() {

    }

    private fun onViewModelEnrollActivitySuccess(activityEnrolment: ActivityEnrolment) {
        progress.value = 100
    }

    private fun onViewModelEnrollActivityError(e: Throwable) {
        onErrorOccurred(e)
        progress.value = -1
    }
}