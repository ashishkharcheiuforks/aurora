package com.czxbnb.aurora.ui.activityDetail

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.czxbnb.aurora.ERROR_TAG
import com.czxbnb.aurora.base.BaseData
import com.czxbnb.aurora.base.BaseViewModel
import com.czxbnb.aurora.manager.SharedPreferenceManager
import com.czxbnb.aurora.model.activity.Activity
import com.czxbnb.aurora.model.activity_enroll.ActivityEnroll
import com.czxbnb.aurora.network.ActivityApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.ClassCastException
import javax.inject.Inject

class ActivityConfirmViewModel(val context: Context) : BaseViewModel() {
    @Inject
    lateinit var activityApi: ActivityApi
    private val activity: MutableLiveData<Activity> = MutableLiveData()
    private lateinit var enrollSubscription: Disposable
    val progress: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()


    fun loadActivity(activity: Activity) {
        this.activity.value = activity
    }

    fun getActivity(): MutableLiveData<Activity> {
        return activity
    }

    fun enrollActivity(userId: String, activityId: String) {
        enrollSubscription = activityApi.enroll(
            SharedPreferenceManager.getInstance(context)?.token,
            userId,
            activityId
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onEnrollActivityStart() }
            .doOnTerminate { onEnrollActivityFinish() }
            .subscribe(
                { result -> onEnrollActivitySuccess(result) },
                { error -> onEnrollActivityError(error) }
            )
    }

    private fun onEnrollActivityStart() {
        progress.value = 1
    }

    private fun onEnrollActivityFinish() {

    }

    private fun onEnrollActivitySuccess(activityEnroll: BaseData<ActivityEnroll>) {
        progress.value = 100
    }

    private fun onEnrollActivityError(e: Throwable) {
        try {
            val errorBody = JSONObject((e as HttpException).response().errorBody()!!.string())
            errorMessage.value = errorBody.getString(ERROR_TAG)
        } catch (ex: JSONException) {
            errorMessage.value = e.message
        }
        progress.value = -1
    }
}