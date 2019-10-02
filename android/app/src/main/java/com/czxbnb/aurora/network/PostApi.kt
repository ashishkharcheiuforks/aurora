package com.czxbnb.aurora.network

import com.czxbnb.aurora.model.post.Post
import io.reactivex.Observable
import retrofit2.http.GET

interface PostApi {
    @GET("/posts")
    fun getPosts(): Observable<List<Post>>


}