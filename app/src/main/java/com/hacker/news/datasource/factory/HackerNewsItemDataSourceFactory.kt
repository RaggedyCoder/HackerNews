package com.hacker.news.datasource.factory

import com.hacker.news.api.response.ItemInfoResponse
import com.hacker.news.api.service.HackerNewsApiService
import com.hacker.news.datasource.HackerNewsItemDataSource
import java.util.concurrent.Executor

class HackerNewsItemDataSourceFactory(
    private val hackerNewsApiService: HackerNewsApiService,
    private val retryExecutor: Executor
) : NetworkDataSourceFactory<Int, ItemInfoResponse>() {

    private val itemIds = mutableListOf<Int>()

    fun updateItemIds(itemIds: List<Int>) {
        this.itemIds.clear()
        this.itemIds.addAll(itemIds)
    }

    override fun create() = HackerNewsItemDataSource(itemIds, hackerNewsApiService, retryExecutor).apply {
        sourceLiveData.postValue(this)
    }
}