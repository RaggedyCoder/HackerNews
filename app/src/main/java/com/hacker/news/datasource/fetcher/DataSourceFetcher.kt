package com.hacker.news.datasource.fetcher

import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.hacker.news.datasource.factory.NetworkDataSourceFactory
import com.hacker.news.model.Listing
import java.util.concurrent.Executor

abstract class DataSourceFetcher<Key, Model>(pageSize: Int, private val networkExecutor: Executor) {
    private var pagedListConfig = PagedList.Config.Builder()
        .setPageSize(pageSize)
        .setInitialLoadSizeHint(pageSize / 2)
        .setEnablePlaceholders(true)
        .build()

    abstract val dataSourceFactory: NetworkDataSourceFactory<Key, Model>

    protected fun getPagedListing() = Listing(
        LivePagedListBuilder(dataSourceFactory, pagedListConfig).setFetchExecutor(networkExecutor).build(),
        Transformations.switchMap(dataSourceFactory.sourceLiveData) {
            it.networkState
        },
        Transformations.switchMap(dataSourceFactory.sourceLiveData) {
            it.networkState
        },
        refresh = {
            dataSourceFactory.sourceLiveData.value?.invalidate()
        },
        retry = {
            dataSourceFactory.sourceLiveData.value?.retryAllFailed()
        })
}