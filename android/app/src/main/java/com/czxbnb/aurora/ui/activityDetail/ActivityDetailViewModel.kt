package com.czxbnb.aurora.ui.activityDetail

import android.content.Context
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
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
    // private lateinit var subscription: Disposable
    private val activity : MutableLiveData<Activity> = MutableLiveData()

    fun loadActivity(activity : Activity) {
        this.activity.value = activity
    }

    fun getActivity(): MutableLiveData<Activity> {
        return activity
    }

    override fun onCleared() {
        super.onCleared()
    }
}