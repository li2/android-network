/*
 * Created by Weiyi Li on 2019-11-02.
 * https://github.com/li2
 */
package me.li2.android.network.connectivity

import java.io.IOException

object NoNetworkException: IOException("No network available, please check your WIFI or Cellular connection")