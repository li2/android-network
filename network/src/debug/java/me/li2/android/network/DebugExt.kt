package me.li2.android.network

import android.content.Context
import android.os.Build
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY

fun configNetworkBuild(context: Context) {
    if ("robolectric" != Build.FINGERPRINT) {
        Stetho.initializeWithDefaults(context)
    }
}

fun OkHttpClient.Builder.addNetworkInterceptor(): OkHttpClient.Builder = this
        .addNetworkInterceptor(StethoInterceptor())
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(BODY))

