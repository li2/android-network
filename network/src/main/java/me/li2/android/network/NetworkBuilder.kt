/*
 * Created by Weiyi Li on 2019-11-02.
 * https://github.com/li2
 */
package me.li2.android.network

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.serialization.json.Json
import me.li2.android.network.interceptor.RequestInterceptor
import me.li2.android.network.interceptor.ResponseInterceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object NetworkBuilder {

    inline fun <reified T> buildRetrofitAdapter(
            context: Context,
            baseUrl: String,
            requestInterceptor: RequestInterceptor,
            responseInterceptor: ResponseInterceptor,
            converterFactory: Converter.Factory = moshiConverterFactory,
            timeout: Long = TIMEOUT): T {
        val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(requestInterceptor)
                    .addInterceptor(responseInterceptor)
                    .addInterceptor(ChuckInterceptor(context))
                    .addNetworkInterceptor()
                    .readTimeout(timeout, TimeUnit.SECONDS)
                    .connectTimeout(timeout, TimeUnit.SECONDS)
                    .writeTimeout(timeout, TimeUnit.SECONDS)
                    .build()

        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(T::class.java)
    }

    val moshiConverterFactory: MoshiConverterFactory =
            MoshiConverterFactory.create(Moshi.Builder()
                    .add(Wrapped.ADAPTER_FACTORY)
                    .add(KotlinJsonAdapterFactory())
                    .build())

    /**
     * Return a [Converter.Factory] which uses Kotlin serialization.
     * Json.nonstrict to fix JsonUnknownKeyException: Strict JSON encountered unknown key: display_name
     */
    val ktSerializationConverterFactory
        get() = Json.nonstrict.asConverterFactory("application/json".toMediaType())

    const val TIMEOUT = 3000L
}
