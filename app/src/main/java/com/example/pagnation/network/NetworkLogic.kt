package com.example.pagnation.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

@Suppress("DEPRECATION")
object NetworkUtils {

    fun isInternetAvailable(context: Context): Boolean {
        return isWifiTurnedOn(context) || isMobileDataTurnedOn(context)
    }

    private fun isWifiTurnedOn(context: Context): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        connectivityManager?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                it.getNetworkCapabilities(it.activeNetwork)?.apply {
                    return when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        else -> false
                    }
                }
            } else {
                it.activeNetworkInfo?.let { networkInfo ->
                    return when (networkInfo.type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        else -> false
                    }
                }
            }
        }
        return false
    }

    private fun isMobileDataTurnedOn(context: Context): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        connectivityManager?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                it.getNetworkCapabilities(it.activeNetwork)?.apply {
                    return when {
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        else -> false
                    }
                }
            } else {
                it.activeNetworkInfo?.let { networkInfo ->
                    return when (networkInfo.type) {
                        ConnectivityManager.TYPE_MOBILE -> true
                        else -> false
                    }
                }
            }
        }
        return false
    }
}