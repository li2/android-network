/*
 * Created by Weiyi Li on 2019-11-02.
 * https://github.com/li2
 */
package me.li2.android.network.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

abstract class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder().run {
            interceptRequest(this)
            build()
        }
        return chain.proceed(newRequest)
    }

    abstract fun interceptRequest(requestBuilder: Request.Builder)
}