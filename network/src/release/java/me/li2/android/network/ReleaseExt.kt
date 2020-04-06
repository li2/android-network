package me.li2.android.network

import okhttp3.OkHttpClient

fun Context.configNetworkBuild() {
    // do nothing
}

// Do nothing
fun OkHttpClient.Builder.addNetworkInterceptor(): OkHttpClient.Builder = this
