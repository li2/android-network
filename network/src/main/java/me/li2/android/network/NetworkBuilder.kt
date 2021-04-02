/*
 * Created by Weiyi Li on 2019-11-02.
 * https://github.com/li2
 */
@file:Suppress("unused")

package me.li2.android.network

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.readystatesoftware.chuck.ChuckInterceptor
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object NetworkBuilder {

    inline fun <reified T> buildRetrofitAdapter(
            context: Context,
            baseUrl: String,
            interceptors: List<Interceptor> = emptyList(),
            converterFactory: Converter.Factory = moshiConverterFactory,
            timeout: Long = TIMEOUT,
            debug: Boolean = false): T {
        val okHttpClient = OkHttpClient.Builder()
                .apply {
                    interceptors.forEach { interceptor ->
                        addInterceptor(interceptor)
                    }
                    if (debug) {
                        addNetworkInterceptor(StethoInterceptor())
                        addNetworkInterceptor(HttpLoggingInterceptor().setLevel(BODY))
                        addInterceptor(ChuckInterceptor(context))
                    }
                }
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
/*
    //Unresolved reference: nonstrict
    val ktSerializationConverterFactory
        get() = Json.nonstrict.asConverterFactory("application/json".toMediaType())
*/

    const val TIMEOUT = 3000L
}
