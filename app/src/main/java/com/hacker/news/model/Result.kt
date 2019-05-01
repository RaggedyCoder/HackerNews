package com.hacker.news.model

import com.hacker.news.model.Result.Companion.failure
import com.hacker.news.model.Result.Companion.success

sealed class Result<A, B : Error> {
    companion object {
        @JvmStatic
        fun <A, B : Error> success(value: A): Result<A, B> = Success(value)

        @JvmStatic
        fun <A, B : Error> failure(error: B): Result<A, B> = Failure(error)
    }
}

data class Success<A, B : Error> internal constructor(val value: A) : Result<A, B>()
data class Failure<A, B : Error> internal constructor(val error: B) : Result<A, B>()

fun <A, B : Error> A.asSuccess(): Result<A, B> = success(this)
fun <A, B : Error> B.asFailure(): Result<A, B> = failure(this)