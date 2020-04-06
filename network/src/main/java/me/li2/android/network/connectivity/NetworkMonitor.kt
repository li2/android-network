package me.li2.android.network.connectivity

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.core.content.getSystemService

class NetworkMonitor(private val context: Context) : NetworkConnectivityListener {

    // https://stackoverflow.com/a/53078141/2722270
    @Suppress("DEPRECATION")
    @SuppressLint("MissingPermission")
    override fun isConnected(): Boolean {
        context.getSystemService<ConnectivityManager>()?.let { cm ->
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                cm.activeNetworkInfo?.let { networkInfo ->
                    return networkInfo.isConnected
                            && (networkInfo.type == ConnectivityManager.TYPE_WIFI || networkInfo.type == ConnectivityManager.TYPE_MOBILE || networkInfo.type == NetworkCapabilities.TRANSPORT_VPN)
                }

            } else {
                cm.activeNetwork?.let { network ->
                    cm.getNetworkCapabilities(network)?.let { capabilities ->
                        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
                    }
                }
            }
        }
        return false
    }
}