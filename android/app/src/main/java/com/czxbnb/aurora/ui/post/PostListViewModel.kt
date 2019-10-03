package com.czxbnb.aurora.ui.post

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.czxbnb.aurora.adapter.PostListAdapter
import com.czxbnb.aurora.base.BaseViewModel
import com.czxbnb.aurora.model.post.Post
import com.czxbnb.aurora.model.post.PostDao
import com.czxbnb.aurora.network.PostApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostListViewModel(private val postDao: PostDao) : BaseViewModel() {
    @Inject
    lateinit var postApi: PostApi
    private lateinit var subscription: Disposable
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadPosts() }
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val postListAdapter: PostListAdapter = PostListAdapter()

    init {
        loadPosts()
    }

    private fun loadPosts() {
        /**
         * From callable: omit one data set only
         * Concat map: Map values to inner observable
         * Then, if the local database is empty, fetch data from remote source, cache it and omit.
         * Otherwise, omit the data from database
         */
        subscription = Observable.fromCallable { postDao.all }
            .concatMap { dbPostList ->
                if (dbPostList.isEmpty())
                    postApi.getPosts().concatMap { apiPostList ->
                        postDao.insertAll(*apiPostList.toTypedArray())
                        Observable.just(apiPostList)
                    }
                else
                    Observable.just(dbPostList)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { result -> onRetrievePostListSuccess(result) },
                { error -> onRetrievePostListError(error) }
            )
    }

    private fun onRetrievePostListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrievePostListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrievePostListSuccess(postList: List<Post>) {
        postListAdapter.updatePostList(postList)
    }

    private fun onRetrievePostListError(error: Throwable) {
        errorMessage.value = error.message
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}