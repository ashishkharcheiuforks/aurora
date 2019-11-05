package com.czxbnb.aurora.model.activity

interface ActivityCallback {
    fun onLoadActivityStart()

    fun onLoadActivityFinish()

    fun onLoadActivitySuccess(activityList: List<Activity>)

    fun onLoadActivityError(e: Throwable)
}