package me.li2.android.network.connectivity

interface NetworkConnectivityListener {
    /**
     * Indicates whether network connectivity exists.
     * @return true if network connectivity exists, false otherwise.
     */
    fun isConnected(): Boolean
}