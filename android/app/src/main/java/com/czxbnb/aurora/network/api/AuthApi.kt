package com.czxbnb.aurora.network.api

import com.czxbnb.aurora.base.BaseData
import com.czxbnb.aurora.model.user.User
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {
    /**
     * Login and get the long-term token
     * @param username: username
     * @param password: password
     * @return user instance with user information and token
     */
    @FormUrlEncoded
    @POST("api/v1//login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Observable<User>
}