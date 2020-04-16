/*
 * Created by Weiyi Li on 2019-11-03.
 * https://github.com/li2
 */
package me.li2.android.network.interceptor

import me.li2.android.network.NetworkBuilder
import okhttp3.Request

/**
 * Used as a default argument value for [NetworkBuilder]
 */
class DefaultRequestInterceptor: RequestInterceptor() {
    override fun interceptRequest(requestBuilder: Request.Builder) {
        // do nothing
    }
}
