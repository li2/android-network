/*
 * Copyright (C) 2017 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.li2.android.network.json

import com.squareup.moshi.*
import java.io.IOException
import java.lang.reflect.Type

// https://github.com/square/moshi/blob/master/examples/src/main/java/com/squareup/moshi/recipes/Unwrap.java

class EnvelopeJsonAdapter internal constructor(private val delegate: JsonAdapter<Envelope<*>>) : JsonAdapter<Any>() {

    @Retention
    @JsonQualifier
    annotation class Enveloped

    internal class Envelope<T> internal constructor(internal val data: T)

    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): Any? {
        return delegate.fromJson(reader)?.data
    }

    @Throws(IOException::class)
    override fun toJson(writer: JsonWriter, value: Any?) {
        value?.let { delegate.toJson(writer, Envelope(value)) }
    }

    companion object {
        val FACTORY: Factory = object : Factory {
            override fun create(
                    type: Type, annotations: Set<Annotation>, moshi: Moshi): JsonAdapter<*>? {
                val delegateAnnotations = Types.nextAnnotations(annotations, Enveloped::class.java)
                        ?: return null
                val envelope = Types.newParameterizedTypeWithOwner(EnvelopeJsonAdapter::class.java, Envelope::class.java, type)
                val delegate = moshi.nextAdapter<Envelope<*>>(this, envelope, delegateAnnotations)
                return EnvelopeJsonAdapter(delegate)
            }
        }
    }
}
