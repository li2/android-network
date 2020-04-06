package me.li2.android.network

import android.content.Context
import okhttp3.OkHttpClient

fun Context.configNetworkBuild() {
    // do nothing
}

// Do nothing
fun OkHttpClient.Builder.addNetworkInterceptor(context: Context): OkHttpClient.Builder = this
