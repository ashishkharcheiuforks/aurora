package com.czxbnb.aurora.base

data class BaseData<T> (
    val status: Int,
    val error: String,
    val data: T?
)
