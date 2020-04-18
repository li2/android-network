/*
 * Created by Weiyi Li on 2019-11-02.
 * https://github.com/li2
 */
package me.li2.android.network.interceptor

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

abstract class RequestInterceptor : Interceptor {

    abstract var urlBuilder: (HttpUrl.Builder.() -> Unit)

    abstract var requestBuilder: (Request.Builder.() -> Unit)

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newUrl = originalRequest.url.newBuilder().run {
            urlBuilder.invoke(this)
            build()
        }

        val newRequest = originalRequest.newBuilder().run {
            requestBuilder.invoke(this)
            url(newUrl)
            build()
        }
        return chain.proceed(newRequest)
    }
}
