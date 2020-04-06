/*
 * Created by Weiyi Li on 2019-11-03.
 * https://github.com/li2
 */
package me.li2.android.network.json

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

object SerializationUtils {
    inline fun <reified T> fromJson(json: String): T? {
        val moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<T> = moshi.adapter(T::class.java)
        return adapter.fromJson(json)
    }
}
