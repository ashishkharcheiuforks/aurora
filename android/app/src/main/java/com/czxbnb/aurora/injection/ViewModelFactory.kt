package com.czxbnb.aurora.injection

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.czxbnb.aurora.model.AppDatabase
import com.czxbnb.aurora.ui.activity.ActivityItemViewModel
import com.czxbnb.aurora.ui.activity.ActivityViewModel
import com.czxbnb.aurora.ui.activityDetail.ActivityConfirmFragment
import com.czxbnb.aurora.ui.activityDetail.ActivityConfirmViewModel
import com.czxbnb.aurora.ui.activityDetail.ActivityDetailActivity
import com.czxbnb.aurora.ui.activityDetail.ActivityDetailViewModel
import com.czxbnb.aurora.ui.auth.login.LoginViewModel
import com.czxbnb.aurora.ui.home.HomeActivityViewModel
import com.czxbnb.aurora.ui.home.HomeViewModel
import com.czxbnb.aurora.ui.main.MainViewModel
import com.czxbnb.aurora.ui.post.PostListViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(PostListViewModel::class.java) -> {
                val db = AppDatabase.getInstance(context)
                @Suppress("UNCHECKED_CAST")
                return PostListViewModel(db!!.postDao()) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(context) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(context) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(context) as T
            }
            modelClass.isAssignableFrom(HomeActivityViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return HomeActivityViewModel() as T
            }
            modelClass.isAssignableFrom(ActivityViewModel::class.java) -> {
                val db = AppDatabase.getInstance(context)
                @Suppress("UNCHECKED_CAST")
                return ActivityViewModel(context) as T
            }
            modelClass.isAssignableFrom(ActivityItemViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return ActivityItemViewModel() as T
            }
            modelClass.isAssignableFrom(ActivityDetailViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return ActivityDetailViewModel(context) as T
            }
            modelClass.isAssignableFrom(ActivityConfirmViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return ActivityConfirmViewModel(context) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}