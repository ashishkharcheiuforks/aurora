package com.czxbnb.aurora.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.czxbnb.aurora.model.activity.Activity
import com.czxbnb.aurora.model.activity.ActivityDao
import com.czxbnb.aurora.model.post.Post
import com.czxbnb.aurora.model.post.PostDao

@Database(entities = arrayOf(Post::class, Activity::class), version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao() : PostDao
    abstract fun activityDao() : ActivityDao
}