package com.example.pagnation.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import timber.log.Timber


@Suppress("UNCHECKED_CAST")
abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {

        val response = call.invoke()

        when {
            response.isSuccessful -> {
                Timber.d("SafeApiRequest success")
                return response.body()!!
            }
            response.code() == 400 -> {
                JSONObject(response.errorBody()!!.string()).let {
                    throw BadRequestException(it.getString("message"))
                }
            }
            else -> {
                Timber.d("SafeApiRequest error")
                val message = StringBuilder()
                var error: String? = null
                withContext(Dispatchers.IO) {
                    error = response.errorBody()?.string()
                }
                error?.let {
                    try {
                        message.append(JSONObject(it).getString("message"))
                    } catch (e: JSONException) {
                        message.append("Error body not found.")
                    }
                }
                val errorMessage = "Error code : ${response.code()}\n$message"
                when (response.code()) {
                    400 -> throw BadRequestException(errorMessage)
                    401 -> throw AuthenticationFailureException(errorMessage)
                    403 -> throw ForbiddenResourceException(errorMessage)
                    404 -> throw UrlNotFoundException(errorMessage)
                    405 -> throw NotSupportedOrNotAllowedException(errorMessage)
                    429 -> throw TooManyRequestsException(errorMessage)
                    500 -> throw ApiServerException(errorMessage)
                    503 -> throw ServiceUnavailableException(errorMessage)
                    else -> throw UnknownApiException(errorMessage)
                }
            }
        }
    }
}