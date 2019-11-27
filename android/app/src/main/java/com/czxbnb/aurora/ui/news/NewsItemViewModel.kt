package com.czxbnb.aurora.ui.news

import androidx.lifecycle.MutableLiveData
import com.czxbnb.aurora.base.BaseViewModel
import com.czxbnb.aurora.model.news.Article

class NewsItemViewModel : BaseViewModel() {
    private lateinit var article: Article
    private val newsTitle = MutableLiveData<String>()
    private val newsAuthor = MutableLiveData<String>()
    private val newsImage = MutableLiveData<String>()
    
    fun bind(article: Article) {
        this.article = article
        newsTitle.value = article.title
        newsAuthor.value = article.author
        newsImage.value = article.urlToImage
    }

    fun getNewsTitle(): MutableLiveData<String> {
        return newsTitle
    }

    fun getNewsAuthor(): MutableLiveData<String> {
        return newsAuthor
    }

    fun getNewsImage(): MutableLiveData<String> {
        return newsImage
    }
}