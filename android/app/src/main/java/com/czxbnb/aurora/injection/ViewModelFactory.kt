package com.czxbnb.aurora.injection

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.czxbnb.aurora.model.AppDatabase
import com.czxbnb.aurora.ui.auth.login.LoginViewModel
import com.czxbnb.aurora.ui.home.HomeActivityViewModel
import com.czxbnb.aurora.ui.home.HomeViewModel
import com.czxbnb.aurora.ui.main.MainViewModel
import com.czxbnb.aurora.ui.post.PostListViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(PostListViewModel::class.java) -> {
                val db =
                    Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "aurora")
                        .build()
                @Suppress("UNCHECKED_CAST")
                return PostListViewModel(db.postDao()) as T
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
                val db = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "aurora")
                    .build()
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(context, db.activityDao()) as T
            }
            modelClass.isAssignableFrom(HomeActivityViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return HomeActivityViewModel() as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}