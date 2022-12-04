package com.s9dp.newsstory.data.protodatastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.s9dp.newsstory.UserPreferences
import java.io.InputStream
import java.io.OutputStream

object DataStoreSetting : Serializer<UserPreferences>{

    override val defaultValue: UserPreferences
        get() = UserPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserPreferences {
        try {
            return UserPreferences.parseFrom(input)
        }catch (e: InvalidProtocolBufferException){
            throw CorruptionException("cannot read protocol",e)
        }
    }
    override suspend fun writeTo(t: UserPreferences, output: OutputStream)  = t.writeTo(output)
}