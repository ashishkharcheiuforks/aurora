package com.czxbnb.aurora.network

import com.czxbnb.aurora.base.BaseData
import com.czxbnb.aurora.model.activity.Activity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header

interface ActivityApi {
    /**
     * Get a list of activity
     * @param token user's token
     */
    @GET("api/v1/activity")
    fun getActivities(
        @Header("Authorization") token: String?
    ): Observable<BaseData<List<Activity>>>
}