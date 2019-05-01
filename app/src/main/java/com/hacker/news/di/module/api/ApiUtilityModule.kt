package com.hacker.news.di.module.api

import com.hacker.news.api.ApiConstants.HACKER_NEWS_BASE_URL
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_API_CACHE_MAX_AGE
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_API_CACHE_MAX_STALE
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_API_HACKER_NEWS_BASE_URL
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_HTTP_CONNECT_TIMEOUT
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_HTTP_READ_TIMEOUT
import com.hacker.news.di.scope.HackerNewsAppScope
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ApiUtilityModule {

    @Provides
    @HackerNewsAppScope
    @Named(QUALIFIER_NAME_HTTP_CONNECT_TIMEOUT)
    fun provideConnectTimeout() = 60L // This value is in second

    @Provides
    @HackerNewsAppScope
    @Named(QUALIFIER_NAME_HTTP_READ_TIMEOUT)
    fun provideReadTimeout() = 60L // This value is in second

    @Provides
    @HackerNewsAppScope
    @Named(QUALIFIER_NAME_API_CACHE_MAX_AGE)
    fun provideApiCacheMaxAge() = 30 // This value is in second

    @Provides
    @HackerNewsAppScope
    @Named(QUALIFIER_NAME_API_CACHE_MAX_STALE)
    fun provideApiCacheMaxStale() = 60 * 60 * 24 // This value is in second


    @Provides
    @HackerNewsAppScope
    @Named(QUALIFIER_NAME_API_HACKER_NEWS_BASE_URL)
    fun provideHackerNewsBaseUrl() = HACKER_NEWS_BASE_URL
}
