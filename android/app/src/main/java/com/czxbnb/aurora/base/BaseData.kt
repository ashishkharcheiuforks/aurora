package com.czxbnb.aurora.base

data class BaseData<T> (
    val status: String ,
    val error: String,
    val data: T
)
