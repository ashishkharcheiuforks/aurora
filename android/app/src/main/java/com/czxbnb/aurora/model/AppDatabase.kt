package com.czxbnb.aurora.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.czxbnb.aurora.model.activity.Activity
import com.czxbnb.aurora.model.activity.ActivityDao
import com.czxbnb.aurora.model.post.Post
import com.czxbnb.aurora.model.post.PostDao

@Database(entities = arrayOf(Post::class, Activity::class), version = 2)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var instance: AppDatabase? = null
        fun getInstance(context: Context) : AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class.java) {
                    if (instance == null) {
                        instance = Room
                            .databaseBuilder(
                                context.applicationContext,
                                AppDatabase::class.java,
                                "aurora"
                            )
                            .build()
                    }
                }
            }
            return instance
        }
    }

    abstract fun postDao(): PostDao
    abstract fun activityDao(): ActivityDao
}