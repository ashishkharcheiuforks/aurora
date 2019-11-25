package com.czxbnb.aurora.ui.profile

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.czxbnb.aurora.AuroraApplication
import com.czxbnb.aurora.base.BaseViewModel
import com.czxbnb.aurora.manager.SharedPreferenceManager
import com.czxbnb.aurora.model.user.User

class ProfileViewModel: BaseViewModel() {
    // Live data
    val userLiveData: MutableLiveData<User> = MutableLiveData()

    init {
        loadUser()
    }

    private fun loadUser() {
        userLiveData.value = SharedPreferenceManager.getInstance(AuroraApplication.getApplicationContext())?.user
    }
}