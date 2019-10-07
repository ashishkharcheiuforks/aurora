package com.czxbnb.aurora.model.activity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ActivityDao{
    @get:Query("SELECT * FROM activity")
    val all: List<Activity>

    @Insert
    fun insertAll(vararg activity: Activity)
}