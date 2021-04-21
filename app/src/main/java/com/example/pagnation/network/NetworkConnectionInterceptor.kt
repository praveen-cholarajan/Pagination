package com.example.pagnation.network

import android.content.Context
import com.example.pagnation.network.NetworkUtils.isInternetAvailable
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        if (!isInternetAvailable(context)) {
            throw NoInternetException("Kindly check your connectivity!")
        } else {
            try {
                return chain.proceed(chain.request())
            } catch (e: Exception) {
                throw ApiException(e.message ?: e.toString())
            }
        }
    }
}