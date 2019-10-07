package com.czxbnb.aurora.model.activity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Activity(
    @PrimaryKey val id: String,
    val title: String,
    val content: String,
    var time: String,
    val duration: String,
    val requirements: String,
    val image: String
)