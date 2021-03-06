package com.czxbnb.aurora.network.converter

import android.widget.Toast
import com.czxbnb.aurora.AuroraApplication
import com.czxbnb.aurora.base.BaseData
import com.czxbnb.aurora.model.event_bus.MessageEvent
import com.google.android.gms.common.api.ApiException
import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonToken
import okhttp3.ResponseBody
import org.greenrobot.eventbus.EventBus
import retrofit2.Converter
import java.io.IOException
import java.lang.Exception
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


class AuroraResponseBodyConverter<T>(private val converter: Converter<ResponseBody, BaseData<T>>) :
    Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(responseBody: ResponseBody): T? {
        val response = converter.convert(responseBody)
        if (response.status !in 200..299) {
            EventBus.getDefault().post(MessageEvent(response.error))
        }
        return response.data
    }
}