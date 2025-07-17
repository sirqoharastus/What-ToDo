package com.abdulqohar.whattodo.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

object ApiCallsHandler {

    const val MESSAGE_KEY = "message"
    const val ERROR_KEY = "error"
    const val DATA_KEY = "data"
    const val CAP_MESSAGE_KEY = "Message"

    suspend inline fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher,
        crossinline apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(dispatcher) {
            try {

                Resource.Success(apiCall.invoke())
            } catch (exception: Exception) {
                exception.printStackTrace()
                when (exception) {
                    is java.net.UnknownHostException -> Resource.Error("An error occurred, please check your network connection")
                    is IOException -> Resource.Error("Sorry something went wrong,Kindly check your internet and retry")
                    is HttpException -> {
                        val code = exception.code()
                        val errorResponse = exception.response()?.errorBody()?.let {
                            getErrorMessage(it)
                        }
                        Resource.Error("$errorResponse")
                    }
                    else -> {

                        Resource.Error(exception.message ?: "An error occurred", null)
                    }
                }
            }
        }
    }

    fun getErrorMessage(responseBody: ResponseBody): String {
        return try {
            val jsonObject = JSONObject(responseBody.string())
            when {
                jsonObject.has(MESSAGE_KEY) && jsonObject.has(DATA_KEY) -> {
                    val jsonErrorObject = jsonObject.getJSONObject(DATA_KEY)
                    jsonErrorObject.getString(MESSAGE_KEY)
                }
                jsonObject.has(MESSAGE_KEY) -> jsonObject.getString(MESSAGE_KEY)
                jsonObject.has(ERROR_KEY) -> jsonObject.getString(ERROR_KEY)
                jsonObject.has(CAP_MESSAGE_KEY) -> jsonObject.getString(CAP_MESSAGE_KEY)
                else -> "An error occurred, please check your network connection"
            }
        } catch (e: Exception) {
           "An error occurred, please check your internet connection"
        }
    }
}

