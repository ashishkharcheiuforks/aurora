package com.czxbnb.aurora.model.activity_enrolment

import androidx.room.PrimaryKey

data class ActivityEnrolment(
    @PrimaryKey val id: String,
    var user_id: String,
    var activity_id: String
)