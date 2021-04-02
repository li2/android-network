/*
 * Created by Weiyi Li on 2019-11-02.
 * https://github.com/li2
 */
package me.li2.android.network.interceptor

import me.li2.android.network.connectivity.NetworkConnectivityListener
import me.li2.android.network.connectivity.NoNetworkException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.HttpURLConnection.HTTP_OK

abstract class ResponseInterceptor(private val nc: NetworkConnectivityListener) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!nc.isConnected()) {
            throw NoNetworkException
        }
        val request = chain.request()
        val response = chain.proceed(request)
        val statusCode = response.code
        if (statusCode < HTTP_OK || statusCode > 299) {
            handleErrorResponse(response)
        }
        return response
    }

    abstract fun handleErrorResponse(response: Response)
}
