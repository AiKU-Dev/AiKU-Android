package com.hyunjung.aiku.core.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.google.protobuf.MessageLite
import java.io.InputStream
import java.io.OutputStream

abstract class ProtoSerializer<T : MessageLite>(
    defaultInstance: T,
    private val parse: (InputStream) -> T
) : Serializer<T> {

    override val defaultValue: T = defaultInstance

    override suspend fun readFrom(input: InputStream): T =
        try {
            parse(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", e)
        }

    override suspend fun writeTo(t: T, output: OutputStream) {
        t.writeTo(output)
    }
}
