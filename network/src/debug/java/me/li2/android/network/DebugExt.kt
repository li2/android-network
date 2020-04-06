package me.li2.android.network

import android.content.Context
import android.os.Build
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY

fun Context.configNetworkBuild() {
    if ("robolectric" != Build.FINGERPRINT) {
        Stetho.initializeWithDefaults(this)
    }
}

fun OkHttpClient.Builder.addNetworkInterceptor(context: Context): OkHttpClient.Builder = this
        .addNetworkInterceptor(StethoInterceptor())
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(BODY))
        .addInterceptor(ChuckInterceptor(context))

