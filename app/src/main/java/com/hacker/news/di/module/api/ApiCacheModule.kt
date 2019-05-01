package com.hacker.news.di.module.api

import android.content.Context
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_API_CACHE_INTERCEPTOR
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_API_CACHE_MAX_AGE
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_API_CACHE_MAX_STALE
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_HTTP_CACHE_DIRECTORY_FILE
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_HTTP_CACHE_FOLDER_NAME
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_HTTP_CACHE_MAX_SIZE
import com.hacker.news.di.scope.HackerNewsAppScope
import com.hacker.news.util.isOnline
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
class ApiCacheModule {

    @Provides
    @HackerNewsAppScope
    @Named(QUALIFIER_NAME_API_CACHE_INTERCEPTOR)
    fun provideCacheInterceptor(
        context: Context,
        @Named(QUALIFIER_NAME_API_CACHE_MAX_AGE) maxAge: Int,
        @Named(QUALIFIER_NAME_API_CACHE_MAX_STALE) maxStale: Int
    ): Interceptor = Interceptor {
        var request = it.request()
        val cacheControl = if (context.isOnline()) {
            // Requesting the cache value, if the api has already been called in getViewModelClass maxAge second/s before timeline.
            CacheControl.Builder().maxAge(maxAge, TimeUnit.SECONDS).build()
        } else {
            // Requesting the cache value, when the network isn't available and if we've any cache for this api that
            // has been cached within maxStale second/s timeline.
            CacheControl.Builder().maxStale(maxStale, TimeUnit.SECONDS).onlyIfCached().build()
        }
        request = request.newBuilder()
            .cacheControl(cacheControl)
            .build()
        return@Interceptor it.proceed(request)
    }

    @Provides
    @HackerNewsAppScope
    fun provideCache(
        @Named(QUALIFIER_NAME_HTTP_CACHE_MAX_SIZE) httpCacheMaxSize: Long,
        @Named(QUALIFIER_NAME_HTTP_CACHE_DIRECTORY_FILE) httpCacheDirectoryFile: File
    ): Cache = Cache(httpCacheDirectoryFile, httpCacheMaxSize)

    @Provides
    @HackerNewsAppScope
    @Named(QUALIFIER_NAME_HTTP_CACHE_DIRECTORY_FILE)
    fun provideHttpCacheDirectoryFile(
        context: Context,
        @Named(QUALIFIER_NAME_HTTP_CACHE_FOLDER_NAME) httpCacheChildPath: String
    ): File = File(context.cacheDir, httpCacheChildPath)

    @Provides
    @HackerNewsAppScope
    @Named(QUALIFIER_NAME_HTTP_CACHE_MAX_SIZE)
    fun provideHttpCacheSize() = (10 * 1024 * 1024).toLong()


    @Provides
    @HackerNewsAppScope
    @Named(QUALIFIER_NAME_HTTP_CACHE_FOLDER_NAME)
    fun provideHttpCacheChildPath() = "http-cache"
}
