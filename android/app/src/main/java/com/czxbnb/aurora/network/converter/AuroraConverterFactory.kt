package com.czxbnb.aurora.network.converter

import com.czxbnb.aurora.base.BaseData
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

@Suppress("UNCHECKED_CAST")
class AuroraConverterFactory(gson: Gson) : Converter.Factory() {
    private val gsonConverterFactory: GsonConverterFactory = GsonConverterFactory.create(gson)

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val wrappedType = object : ParameterizedType {
            override fun getActualTypeArguments(): Array<Type> = arrayOf(type)
            override fun getOwnerType(): Type? = null
            override fun getRawType(): Type = BaseData::class.java
        }
        val gsonConverter: Converter<ResponseBody, *>? =
            gsonConverterFactory.responseBodyConverter(wrappedType, annotations, retrofit)
        return AuroraResponseBodyConverter(gsonConverter as Converter<ResponseBody, BaseData<Any>>)
    }

    override fun requestBodyConverter(
        type: Type?, parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>, retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return gsonConverterFactory.requestBodyConverter(
            type!!,
            parameterAnnotations,
            methodAnnotations,
            retrofit
        )
    }

}