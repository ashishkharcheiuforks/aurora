package com.czxbnb.aurora.ui.activityDetail

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.czxbnb.aurora.ERROR_TAG
import com.czxbnb.aurora.base.BaseData
import com.czxbnb.aurora.base.BaseViewModel
import com.czxbnb.aurora.manager.SharedPreferenceManager
import com.czxbnb.aurora.model.activity.Activity
import com.czxbnb.aurora.network.ActivityApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class ActivityDetailViewModel(val context: Context) : BaseViewModel() {
    @Inject
    lateinit var activityApi: ActivityApi
    private lateinit var subscription: Disposable
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val loadingVisibility = MutableLiveData<Int>().apply { postValue(View.GONE) }
    val imageUrl =  MutableLiveData<String>().apply { postValue("https://images.pexels.com/photos/414612/pexels-photo-414612.jpeg") }

    fun loadActivity(id: String?) {
        if (id == "") {
            return
        }
        subscription = activityApi.getActivityById(SharedPreferenceManager.getInstance(context)?.token, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onLoadActivityListStart() }
                .doOnTerminate { onLoadActivityListFinish() }
                .subscribe(
                    { result -> onLoadActivitySuccess(result.data[0]) },
                    { error -> onLoadActivityError(error) }
                )

    }

    private fun onLoadActivityListStart() {
        loadingVisibility.value = View.VISIBLE
    }

    private fun onLoadActivityListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onLoadActivitySuccess(loadedActivity: Activity) {
        val activity = loadedActivity
        imageUrl.value = activity.image
    }

    private fun onLoadActivityError(e: Throwable) {
        val errorBody = JSONObject((e as HttpException).response().errorBody()!!.string())
        errorMessage.value = errorBody.getString(ERROR_TAG)
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}