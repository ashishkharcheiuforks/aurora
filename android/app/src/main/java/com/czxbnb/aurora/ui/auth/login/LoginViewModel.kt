package com.czxbnb.aurora.ui.auth.login

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.czxbnb.aurora.ui.main.MainActivity
import com.czxbnb.aurora.base.BaseViewModel
import com.czxbnb.aurora.manager.SharedPreferenceManager
import com.czxbnb.aurora.model.user.User
import com.czxbnb.aurora.network.AuthApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import javax.inject.Inject
import org.json.JSONObject
import com.czxbnb.aurora.ERROR_TAG
import com.czxbnb.aurora.model.user.UserCallback
import com.czxbnb.aurora.model.user.UserRepository


class
LoginViewModel(val context: Context) : BaseViewModel() {
    @Inject
    lateinit var userRepository: UserRepository

    private lateinit var subscription: Disposable
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val loadingVisibility = MutableLiveData<Int>().apply { postValue(View.GONE) }
    val textVisibility = MutableLiveData<Int>().apply { postValue(View.VISIBLE) }
    private var isLoggingIn = false

    fun login(username: String, password: String) {
        // Prevent double click
        if (isLoggingIn) {
            return
        }

        // Check the username and password
        if (username == "") {
            errorMessage.value = "Username cannot be blank"
            return
        } else if (password == "") {
            errorMessage.value = "Password cannot be blank"
            return
        }

        // Perform login action
        subscription = userRepository.login(username, password, object : UserCallback {
            override fun onLoadUserStart() {
                onLoginStart()
            }

            override fun onLoadUserFinish() {
               onLoginFinish()
            }

            override fun onLoadUserSuccess(user: User) {
                onLoginSuccess(user)
            }

            override fun onLoadUserError(e: Throwable) {
               onLoginError(e)
            }
        })
    }

    private fun onLoginStart() {
        loadingVisibility.value = View.VISIBLE
        textVisibility.value = View.GONE
        isLoggingIn = true
        errorMessage.value = null
    }

    private fun onLoginFinish() {
        loadingVisibility.value = View.GONE
        textVisibility.value = View.VISIBLE
    }

    private fun onLoginSuccess(user: User) {
        // Save the token in preference
        SharedPreferenceManager.getInstance(context)?.token = user.token

        // Redirect to main page
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }

    private fun onLoginError(e: Throwable) {
        val errorBody = JSONObject((e as HttpException).response().errorBody()!!.string())
        errorMessage.value = errorBody.getString(ERROR_TAG)
        isLoggingIn = false
    }
}