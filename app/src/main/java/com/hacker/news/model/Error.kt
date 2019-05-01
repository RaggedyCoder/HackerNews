package com.hacker.news.model

import com.hacker.news.model.Error.Companion.network
import com.hacker.news.model.Error.Companion.other
import com.hacker.news.model.Error.Companion.response
import com.hacker.news.model.Error.Companion.responseWithCache
import retrofit2.HttpException
import java.net.UnknownHostException

sealed class Error {
    abstract val reason: String

    companion object {
        fun network(message: String): Error = NetworkError(message)
        fun response(responseCode: Int, errorBody: String, reason: String): Error =
            ResponseError(responseCode, errorBody, reason)

        fun responseWithCache(responseBody: String, httpException: HttpException): Error =
            ResponseErrorWithCache(responseBody, httpException.message() ?: "No message", httpException)

        fun other(throwable: Throwable): Error = OtherError(throwable.message ?: "No message", throwable)
    }
}

data class NetworkError internal constructor(override val reason: String) : Error()
data class ResponseError internal constructor(
    val responseCode: Int,
    val errorBody: String,
    override val reason: String
) : Error()

data class ResponseErrorWithCache internal constructor(
    val responseBody: String,
    override val reason: String,
    val throwable: Throwable? = null
) : Error()

data class OtherError internal constructor(override val reason: String, val exception: Throwable? = null) : Error()


fun Throwable.asError(): Error = when (this) {
    is UnknownHostException -> network(this.message ?: "IP address of the host could not be determined.")
    is HttpException -> {
        when {
            this.response().errorBody() != null -> try {
                response(this.code(), this.response().errorBody()!!.string(), this.message())
            } catch (ex: Exception) {
                other(ex)
            }
            this.response().raw().cacheResponse() != null -> try {
                if (this.response().raw().cacheResponse()!!.isSuccessful &&
                    this.response().raw().cacheResponse()!!.body() != null
                ) {
                    this.cause
                    responseWithCache(this.response().raw().cacheResponse()!!.body()!!.string(), this)
                } else {
                    other(this)
                }
            } catch (ex: Exception) {
                other(ex)
            }
            else -> other(this)
        }
    }
    else -> other(this)
}