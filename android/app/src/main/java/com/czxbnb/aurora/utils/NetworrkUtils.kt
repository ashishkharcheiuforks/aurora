package com.czxbnb.aurora.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import org.json.JSONObject
import okhttp3.ResponseBody
import retrofit2.HttpException


object NetworkUtils {

    fun getErrorMessage(throwable: HttpException): String? {
        try {
            val responseBody: ResponseBody? = throwable.response().errorBody()
            val jsonObject = JSONObject(responseBody!!.string())
            return jsonObject.getString("message")
        } catch (e: Exception) {
            return e.message
        }
    }


    fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }
}