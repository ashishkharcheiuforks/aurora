package com.czxbnb.aurora.model.user

interface UserCallback {
    fun onLoadUserStart()

    fun onLoadUserFinish()

    fun onLoadUserSuccess(user: User)

    fun onLoadUserError(e: Throwable)
}