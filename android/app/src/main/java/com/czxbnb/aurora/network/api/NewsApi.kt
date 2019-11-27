package com.czxbnb.aurora.network.api

import com.czxbnb.aurora.model.news.News
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("/v2/top-headlines")
    fun getHeadlineNewsByCountry(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String
    ): Observable<News>
}