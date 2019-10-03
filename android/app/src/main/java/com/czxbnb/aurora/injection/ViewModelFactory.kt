package com.czxbnb.aurora.injection

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.czxbnb.aurora.model.AppDatabase
import com.czxbnb.aurora.ui.auth.login.LoginViewModel
import com.czxbnb.aurora.ui.post.PostListViewModel

class ViewModelFactory(private val activity: AppCompatActivity) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostListViewModel::class.java)) {
            val db =
                Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "posts")
                    .build()
            @Suppress("UNCHECKED_CAST")
            return PostListViewModel(db.postDao()) as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            val context: Context = activity
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}