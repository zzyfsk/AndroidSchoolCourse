package com.zzy.androidschoolcourse.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiInfo

class IPUtil {
    private val tag = "IpUtil"

    fun getWifiIP(context: Context): String {
        val connectivityManager = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        val network = connectivityManager.activeNetwork ?: throw RuntimeException("no network")
        val capabilities = connectivityManager.getNetworkCapabilities(network)?: throw RuntimeException("no network")
        return intToIP((capabilities.transportInfo as WifiInfo).ipAddress)

    }

    private fun intToIP(int: Int):String{
        val sb = StringBuilder()
        sb.append(int and 0xFF).append(".")
        sb.append((int shr 8) and 0xFF).append(".")
        sb.append((int shr 16) and 0xFF).append(".")
        sb.append((int shr 24) and 0xFF)
        return sb.toString()
    }

    companion object {
        val ipUtil = IPUtil()
    }

}