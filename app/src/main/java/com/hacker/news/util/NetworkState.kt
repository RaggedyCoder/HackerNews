package com.hacker.news.util

sealed class NetworkState(open val status: Status) {

    companion object {
        val REFRESHING = RefreshingState(Status.REFRESH)
        val LOADING = LoadingState(Status.RUNNING)
        val LOADED = LoadedSate(Status.SUCCESS)
        val COMPLETED = CompleteState(Status.COMPLETED)
        fun error(t: Throwable) = ErrorState(Status.FAILED, t)
    }
}

data class LoadedSate(override val status: Status) : NetworkState(status)
data class RefreshingState(override val status: Status) : NetworkState(status)
data class LoadingState(override val status: Status) : NetworkState(status)
data class CompleteState(override val status: Status) : NetworkState(status)
data class ErrorState(override val status: Status, val error: Throwable) : NetworkState(status)