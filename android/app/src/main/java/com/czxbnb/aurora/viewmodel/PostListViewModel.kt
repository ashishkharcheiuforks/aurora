package com.czxbnb.aurora.viewmodel

import com.czxbnb.aurora.base.BaseViewModel
import com.czxbnb.aurora.network.PostApi
import javax.inject.Inject

class PostViewModel : BaseViewModel() {
    @Inject
    lateinit var postApi: PostApi

}