package com.czxbnb.aurora.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.czxbnb.aurora.model.post.Post
import com.czxbnb.aurora.model.post.PostDao

@Database(entities = arrayOf(Post::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao() : PostDao
}