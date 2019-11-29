package com.czxbnb.aurora.ui.news

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.czxbnb.aurora.base.BaseViewModel
import com.czxbnb.aurora.model.news.News
import com.czxbnb.aurora.model.news.NewsCallback
import com.czxbnb.aurora.model.news.NewsRepository
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class NewsViewModel : BaseViewModel() {
    @Inject
    lateinit var newsRepository: NewsRepository

    // Subscriptions
    private lateinit var newsSubscription: Disposable

    // LiveData
    val newsLoadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val newsRefreshVisibility: MutableLiveData<Boolean> = MutableLiveData()

    // Recycler view adapter
    val newsAdapter: NewsAdapter = NewsAdapter()

    init {
        getNews()
    }

    fun getNews() {
        newsSubscription = newsRepository.getNews("au",
            object : NewsCallback {
                override fun onNewsStart() {
                    onLoadNewsStart()
                }

                override fun onNewsFinish() {
                    onLoadNewsFinish()
                }

                override fun onNewsSuccess(news: News) {
                    onLoadNewsSuccess(news)
                }

                override fun onNewsError(e: Throwable) {
                    onLoadNewsError(e)
                }
            })
    }

    private fun onLoadNewsStart() {
        newsLoadingVisibility.value = View.VISIBLE
    }

    private fun onLoadNewsFinish() {
        newsLoadingVisibility.value = View.GONE
        newsRefreshVisibility.value = false
    }

    private fun onLoadNewsSuccess(news: News) {
        newsAdapter.updateNews(news)
    }

    private fun onLoadNewsError(e: Throwable) {
        onErrorOccurred(e)
    }

    override fun onCleared() {
        super.onCleared()
        newsSubscription.dispose()
    }
}