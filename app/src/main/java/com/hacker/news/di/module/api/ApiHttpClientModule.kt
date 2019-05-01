package com.hacker.news.di.module.api

import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_API_CACHE_INTERCEPTOR
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_API_HACKER_NEWS_OK_HTTP_CLIENT
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_API_HEADER_INTERCEPTOR
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_API_LOGGING_INTERCEPTOR
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_HTTP_CONNECT_TIMEOUT
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_HTTP_READ_TIMEOUT
import com.hacker.news.di.scope.HackerNewsAppScope
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module(
    includes = [
        ApiUtilityModule::class,
        ApiHeaderModule::class,
        ApiCacheModule::class,
        ApiLoggerModule::class
    ]
)
class ApiHttpClientModule {

    @Provides
    @Named(QUALIFIER_NAME_API_HACKER_NEWS_OK_HTTP_CLIENT)
    @HackerNewsAppScope
    fun provideApiOkHttpClient(
        cache: Cache,
        @Named(QUALIFIER_NAME_HTTP_READ_TIMEOUT) readTimeOut: Long,
        @Named(QUALIFIER_NAME_HTTP_CONNECT_TIMEOUT) connectTimeOut: Long,
        @Named(QUALIFIER_NAME_API_HEADER_INTERCEPTOR) httpHeaderInterceptor: Interceptor,
        @Named(QUALIFIER_NAME_API_CACHE_INTERCEPTOR) cacheInterceptor: Interceptor,
        @Named(QUALIFIER_NAME_API_LOGGING_INTERCEPTOR) httpLoggerInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .cache(cache)
        .readTimeout(readTimeOut, TimeUnit.SECONDS)
        .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
        .addInterceptor(httpLoggerInterceptor)
        .addNetworkInterceptor(cacheInterceptor)
        .addNetworkInterceptor(httpHeaderInterceptor)
        .build()
}