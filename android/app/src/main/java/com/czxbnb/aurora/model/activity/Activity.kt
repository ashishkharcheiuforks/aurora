package com.czxbnb.aurora.model.activity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Activity(
    @PrimaryKey val id: String,
    var title: String,
    var content: String,
    var time: String,
    var duration: String,
    var requirements: String,
    var address: String,
    var lat: Double,
    var lng: Double,
    var image: String
) : Serializable