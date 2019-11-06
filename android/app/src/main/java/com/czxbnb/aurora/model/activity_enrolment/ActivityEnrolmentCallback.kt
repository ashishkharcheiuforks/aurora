package com.czxbnb.aurora.model.activity_enrolment

interface ActivityEnrolmentCallback {
    fun onEnrollActivityStart()

    fun onEnrollActivityFinish()

    fun onEnrollActivitySuccess(activityEnrolment: ActivityEnrolment)

    fun onEnrollActivityError(e: Throwable)
}