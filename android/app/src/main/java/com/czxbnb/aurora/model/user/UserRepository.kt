package com.czxbnb.aurora.model.user

import com.czxbnb.aurora.base.BaseRepository
import com.czxbnb.aurora.network.AuthApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserRepository private constructor() : BaseRepository() {
    @Inject
    lateinit var authApi: AuthApi

    companion object {
        @Volatile
        private lateinit var instance: UserRepository

        fun getInstance(): UserRepository {
            if (!::instance.isInitialized) {
                synchronized(UserRepository::class.java) {
                    if (!::instance.isInitialized) {
                        instance = UserRepository()
                    }
                }
            }
            return instance
        }
    }

    fun login(
        username: String,
        password: String,
        userCallback: UserCallback
    ): Disposable {
        return authApi.login(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { userCallback.onLoadUserStart() }
            .doOnTerminate { userCallback.onLoadUserFinish() }
            .subscribe(
                { result -> userCallback.onLoadUserSuccess(result) },
                { error -> userCallback.onLoadUserError(error) }
            )
    }
}