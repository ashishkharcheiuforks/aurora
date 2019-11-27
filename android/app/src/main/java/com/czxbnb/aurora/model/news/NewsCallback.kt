package com.czxbnb.aurora.model.news

interface NewsCallback {
    fun onNewsStart()

    fun onNewsFinish()

    fun onNewsSuccess(news: News)

    fun onNewsError(e: Throwable)
}