package com.czxbnb.aurora.model.activity_enroll

import androidx.room.PrimaryKey

data class ActivityEnroll(
    @PrimaryKey val id: String,
    var user_id: String,
    var activity_id: String
)