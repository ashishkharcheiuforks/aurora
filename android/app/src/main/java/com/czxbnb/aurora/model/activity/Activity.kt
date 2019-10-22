package com.czxbnb.aurora.model.activity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Activity(
    @PrimaryKey val id: String,
    var title: String,
    val content: String,
    var time: String,
    val duration: String,
    val requirements: String,
    val image: String
) : Serializable