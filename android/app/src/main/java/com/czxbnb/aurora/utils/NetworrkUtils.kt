package com.czxbnb.aurora.utils

import org.json.JSONObject
import okhttp3.ResponseBody
import retrofit2.HttpException


class NetworrkUtils {
    companion object {
        fun getErrorMessage(throwable: HttpException): String? {
            try {
                val responseBody: ResponseBody? = throwable.response().errorBody()
                val jsonObject = JSONObject(responseBody!!.string())
                return jsonObject.getString("message")
            } catch (e: Exception) {
                return e.message
            }
        }
    }

}