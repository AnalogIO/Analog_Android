package dk.analog.digitalclipcard.backend

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class ErrorBody(val message: String)

@Suppress("unused") // T is used in extending classes
sealed class ApiResponse<T> {
    companion object {
        private fun getErrorMessage(json: String?): String {
            return if (json == null) "Unknown error"
            else Gson().fromJson(json, ErrorBody::class.java).message
        }

        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(getErrorMessage(error.message))
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(body)
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                ApiErrorResponse(getErrorMessage(errorMsg))
            }
        }
    }
}

/**
 * separate class for HTTP 204 responses so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()

data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()

fun <T> Call<T>.makeCall(onResponse: (ApiResponse<T>) -> Unit) {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            onResponse(ApiResponse.create(response))
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            onResponse(ApiResponse.create(t))
        }
    })
}
