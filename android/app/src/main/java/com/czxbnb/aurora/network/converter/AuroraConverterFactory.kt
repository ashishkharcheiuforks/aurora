package com.czxbnb.aurora.network.converter

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class AuroraConverterFactory private constructor(val gson: Gson?): Converter.Factory() {
    companion object {
        fun create(): AuroraConverterFactory {
            return create(
                Gson()
            )
        }

        fun create(gson: Gson?): AuroraConverterFactory {
            if (gson == null) {
                throw  NullPointerException("gson == null")
            }
            return AuroraConverterFactory(
                gson
            )
        }
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val adapter: TypeAdapter<*> = gson!!.getAdapter(TypeToken.get(type))
        return AuroraResponseBodyConverter(gson, adapter)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        val adapter: TypeAdapter<*> = gson!!.getAdapter(TypeToken.get(type))
        return AuroraRequestBodyConverter(gson, adapter)
    }
}