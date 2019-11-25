package com.czxbnb.aurora.injection

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.czxbnb.aurora.model.AppDatabase
import com.czxbnb.aurora.ui.activity.ActivityItemViewModel
import com.czxbnb.aurora.ui.activity.ActivityViewModel
import com.czxbnb.aurora.ui.activityDetail.ActivityConfirmViewModel
import com.czxbnb.aurora.ui.activityDetail.ActivityDetailViewModel
import com.czxbnb.aurora.ui.login.LoginViewModel
import com.czxbnb.aurora.ui.home.HomeActivityViewModel
import com.czxbnb.aurora.ui.home.HomeViewModel
import com.czxbnb.aurora.ui.main.MainViewModel
import com.czxbnb.aurora.ui.profile.ProfileViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel() as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel() as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel() as T
            }
            modelClass.isAssignableFrom(HomeActivityViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return HomeActivityViewModel() as T
            }
            modelClass.isAssignableFrom(ActivityViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return ActivityViewModel() as T
            }
            modelClass.isAssignableFrom(ActivityItemViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return ActivityItemViewModel() as T
            }
            modelClass.isAssignableFrom(ActivityDetailViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return ActivityDetailViewModel() as T
            }
            modelClass.isAssignableFrom(ActivityConfirmViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return ActivityConfirmViewModel() as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return ProfileViewModel() as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}