package com.czxbnb.aurora.model.news

import com.czxbnb.aurora.base.BaseRepository
import com.czxbnb.aurora.model.activity_enrolment.ActivityEnrolmentRepository
import com.czxbnb.aurora.network.api.NewsApi
import com.czxbnb.aurora.utils.Keys
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsRepository : BaseRepository() {
    @Inject
    lateinit var newsApi: NewsApi

    companion object {
        @Volatile
        private lateinit var instance: NewsRepository

        fun getInstance(): NewsRepository {
            if (!::instance.isInitialized) {
                synchronized(NewsRepository::class.java) {
                    if (!::instance.isInitialized) {
                        instance = NewsRepository()
                    }
                }
            }
            return instance
        }
    }

    fun getNews(
        country: String,
        newsCallback: NewsCallback
    ): Disposable {
        return newsApi.getHeadlineNewsByCountry(
            Keys.getGoogleNewsKey(), country
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { newsCallback.onNewsStart() }
            .doOnTerminate { newsCallback.onNewsFinish() }
            .subscribe(
                { result -> newsCallback.onNewsSuccess(result) },
                { error -> newsCallback.onNewsError(error) }
            )
    }

}