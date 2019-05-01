package com.hacker.news.datasource.fetcher

import com.hacker.news.api.response.ItemInfoResponse
import com.hacker.news.api.service.HackerNewsApiService
import com.hacker.news.datasource.factory.HackerNewsItemDataSourceFactory
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_DATA_SOURCE_NETWORK_EXECUTOR
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_DATA_SOURCE_PAGE_SIZE
import com.hacker.news.model.Listing
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Named

class HackerNewsItemDataSourceFetcher @Inject constructor(
    @Named(QUALIFIER_NAME_DATA_SOURCE_PAGE_SIZE) pageSize: Int,
    @Named(QUALIFIER_NAME_DATA_SOURCE_NETWORK_EXECUTOR) netWorkExecutor: Executor,
    hackerNesApiService: HackerNewsApiService
) : DataSourceFetcher<Int, ItemInfoResponse>(pageSize, netWorkExecutor) {
    override val dataSourceFactory = HackerNewsItemDataSourceFactory(hackerNesApiService, netWorkExecutor)

    fun getStoriesFromIds(storyIds: List<Int>): Listing<ItemInfoResponse> {
        dataSourceFactory.updateItemIds(storyIds)
        return getPagedListing()
    }
}