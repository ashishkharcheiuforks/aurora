package com.czxbnb.aurora.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.czxbnb.aurora.R
import com.czxbnb.aurora.base.BaseViewModel
import com.czxbnb.aurora.network.PostApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostListViewModel : BaseViewModel() {
    @Inject
    lateinit var postApi: PostApi
    private lateinit var subscription: Disposable
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadPosts() }
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    init {
        loadPosts()
    }

    private fun loadPosts() {
        subscription = postApi.getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { onRetrievePostListSuccess() },
                { onRetrievePostListError() }
            )
    }

    private fun onRetrievePostListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrievePostListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrievePostListSuccess() {

    }

    private fun onRetrievePostListError() {
        errorMessage.value = R.string.post_error
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}