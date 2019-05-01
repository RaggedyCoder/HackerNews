package com.hacker.news.datasource

import com.hacker.news.api.response.ItemInfoResponse
import com.hacker.news.api.service.HackerNewsApiService
import com.hacker.news.util.NetworkState
import kotlinx.coroutines.*
import retrofit2.Response
import java.util.concurrent.Executor

class HackerNewsItemDataSource(
    private val itemIds: List<Int>,
    private val hackerNewsApiService: HackerNewsApiService,
    retryExecutor: Executor
) :
    NetworkDataSource<Int, ItemInfoResponse>(retryExecutor) {

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ItemInfoResponse>) {
        initialState.postValue(NetworkState.REFRESHING)
        networkState.postValue(NetworkState.REFRESHING)
        val result = mutableListOf<ItemInfoResponse>()
        val maxRequestSize = Math.min(params.requestedLoadSize, itemIds.size)
        try {
            for (i in 0..maxRequestSize) {
                val response = hackerNewsApiService.getItemInfo(itemIds[i].toString()).execute()
                if (response.isSuccessful && response.body() != null) {
                    result += response.body()!!
                } else {
                    break
                }
            }
            val nextPageKey = if (result.size < itemIds.size) {
                result.size
            } else {
                null
            }
            if (nextPageKey == null) {
                initialState.postValue(NetworkState.COMPLETED)
                networkState.postValue(NetworkState.COMPLETED)
            } else {
                initialState.postValue(NetworkState.LOADED)
                networkState.postValue(NetworkState.LOADED)
            }
            callback.onResult(result.filter {
                it.dead != true || it.deleted != true
            }, null, nextPageKey)
        } catch (ex: Exception) {
            if (result.isEmpty()) {
                initialState.postValue(NetworkState.error(ex))
                networkState.postValue(NetworkState.error(ex))
                retry = {
                    loadInitial(params, callback)
                }
            } else {
                val nextPageKey = if (result.size < itemIds.size) {
                    result.size
                } else {
                    null
                }
                if (nextPageKey == null) {
                    initialState.postValue(NetworkState.COMPLETED)
                    networkState.postValue(NetworkState.COMPLETED)
                } else {
                    initialState.postValue(NetworkState.LOADED)
                    networkState.postValue(NetworkState.LOADED)
                }
                callback.onResult(result.filter {
                    it.dead != true || it.deleted != true
                }, null, nextPageKey)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ItemInfoResponse>) {
        networkState.postValue(NetworkState.LOADING)
        val result = mutableListOf<ItemInfoResponse>()
        try {
            uiScope.launch(Dispatchers.IO) {
                val deferredResultList = mutableListOf<Deferred<Response<ItemInfoResponse>>>()
                val startIndex = params.key
                val endIndex = Math.min(params.key + params.requestedLoadSize, itemIds.size - 1)
                for (i in startIndex..endIndex) {
                    deferredResultList += async {
                        hackerNewsApiService.getItemInfo(itemIds[i].toString()).execute()
                    }
                }
                for (deferredResult in deferredResultList) {
                    val response = deferredResult.await()
                    if (response.isSuccessful && response.body() != null) {
                        result += response.body()!!
                    }
                }
                networkState.postValue(NetworkState.LOADED)
                val nextPageKey = if ((params.key + result.size) < itemIds.size) {
                    params.key + result.size
                } else {
                    null
                }
                networkState.postValue(NetworkState.LOADED)
                callback.onResult(result.filter {
                    it.dead != true || it.deleted != true
                }, nextPageKey)
            }
        } catch (ex: Exception) {
            if (result.isEmpty()) {
                networkState.postValue(NetworkState.error(ex))
                retry = {
                    loadAfter(params, callback)
                }
            } else {
                networkState.postValue(NetworkState.LOADED)
                val nextPageKey = if ((params.key + result.size) < itemIds.size) {
                    params.key + result.size
                } else {
                    null
                }
                if (nextPageKey == null) {
                    networkState.postValue(NetworkState.COMPLETED)
                } else {
                    networkState.postValue(NetworkState.LOADED)
                }
                callback.onResult(result.filter {
                    it.dead != true || it.deleted != true
                }, nextPageKey)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ItemInfoResponse>) {
    }
}