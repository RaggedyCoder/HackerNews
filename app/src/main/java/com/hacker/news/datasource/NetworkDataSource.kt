package com.hacker.news.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.hacker.news.util.NetworkState
import java.util.concurrent.Executor

abstract class NetworkDataSource<Key, Value>(private val retryExecutor: Executor) : PageKeyedDataSource<Key, Value>() {

    /**
     * A reference to a function for the retry event
     */
    protected var retry: (() -> Any)? = null

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter.
     */
    val networkState = MutableLiveData<NetworkState>()

    val initialState = MutableLiveData<NetworkState>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }
}