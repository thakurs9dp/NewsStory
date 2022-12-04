package com.s9dp.newsstory.views.utils.seralizer

import java.lang.reflect.Type
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.StringFormat
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit

@ExperimentalSerializationApi
internal class Factory(private val contentType: MediaType, private val serializer: Serializer) : Converter.Factory() {
    @Suppress("RedundantNullableReturnType") // Retaining interface contract.
    override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
        val loader = serializer.serializer(type)
        return DeserializationStrategyConverter(loader, serializer)
    }

    @Suppress("RedundantNullableReturnType") // Retaining interface contract.
    override fun requestBodyConverter(type: Type, parameterAnnotations: Array<out Annotation>, methodAnnotations: Array<out Annotation>, retrofit: Retrofit): Converter<*, RequestBody>? {
        val saver = serializer.serializer(type)
        return SerializationStrategyConverter(contentType, saver, serializer)
    }
}

@ExperimentalSerializationApi
@JvmName("create")
fun StringFormat.asConverterFactory(contentType: MediaType): Converter.Factory {
    return Factory(contentType, Serializer.FromString(this))
}
