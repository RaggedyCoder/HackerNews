package com.hacker.news.di.module.datasource.fetcher

import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_DATA_SOURCE_NETWORK_EXECUTOR
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_DATA_SOURCE_PAGE_SIZE
import com.hacker.news.di.scope.HackerNewsAppScope
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Named

@Module
class DataSourceFetcherModule {

    @Provides
    @HackerNewsAppScope
    @Named(QUALIFIER_NAME_DATA_SOURCE_PAGE_SIZE)
    fun providePageSize() = 10

    @Provides
    @HackerNewsAppScope
    @Named(QUALIFIER_NAME_DATA_SOURCE_NETWORK_EXECUTOR)
    fun provideNetworkIO(): Executor = Executors.newFixedThreadPool(5)
}