package com.czxbnb.aurora.network

import com.czxbnb.aurora.base.BaseData
import com.czxbnb.aurora.model.activity.Activity
import com.czxbnb.aurora.model.activity_enrolment.ActivityEnrolment
import io.reactivex.Observable
import retrofit2.http.*

interface ActivityApi {
    /**
     * Get a list of activity
     * @param token user's token
     */
    @GET("api/v1/activity")
    fun getActivities(
        @Header("Authorization") token: String?
    ): Observable<BaseData<List<Activity>>>

    @GET("api/v1/activity")
    fun getActivityById(
        @Header("Authorization") token: String?,
        @Query("id") id: String?
    ): Observable<BaseData<List<Activity>>>

    @FormUrlEncoded
    @POST("api/v1/activity/enroll")
    fun enroll(
        @Header("Authorization") token: String?,
        @Field("user_id")user_id: String?,
        @Field("activity_id")activity_id: String?
    ): Observable<BaseData<ActivityEnrolment>>
}