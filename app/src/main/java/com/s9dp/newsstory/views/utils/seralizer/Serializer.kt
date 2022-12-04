package com.s9dp.newsstory.views.utils.seralizer
import kotlinx.serialization.*
import java.lang.reflect.Type
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody

internal sealed class Serializer {
    abstract fun <T> fromResponseBody(loader: DeserializationStrategy<T>, body: ResponseBody): T
    abstract fun <T> toRequestBody(contentType: MediaType, saver: SerializationStrategy<T>, value: T): RequestBody

    @OptIn(ExperimentalSerializationApi::class) // Experimental is only for subtypes.
    protected abstract val format: SerialFormat

    @ExperimentalSerializationApi // serializer(Type) is not stable.
    fun serializer(type: Type): KSerializer<Any> = format.serializersModule.serializer(type)

    @OptIn(ExperimentalSerializationApi::class) // Experimental is only for subtypes.
    class FromString(override val format: StringFormat) : Serializer() {
        override fun <T> fromResponseBody(loader: DeserializationStrategy<T>, body: ResponseBody): T {
            val string = body.string()
            return format.decodeFromString(loader, string)
        }

        override fun <T> toRequestBody(contentType: MediaType, saver: SerializationStrategy<T>, value: T): RequestBody {
            val string = format.encodeToString(saver, value)
            return RequestBody.create(contentType, string)
        }
    }
}

