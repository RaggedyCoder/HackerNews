package com.hacker.news.di.module.api

import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_API_LOGGING_INTERCEPTOR
import com.hacker.news.di.scope.HackerNewsAppScope
import com.hacker.news.util.HackerNewsLogger
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named

@Module
class ApiLoggerModule {
    companion object {
        private val TAG = ApiLoggerModule::class.java
    }

    @Provides
    @HackerNewsAppScope
    @Named(QUALIFIER_NAME_API_LOGGING_INTERCEPTOR)
    fun provideHttpLoggingInterceptor(httpLogger: HttpLoggingInterceptor.Logger): Interceptor =
        HttpLoggingInterceptor(httpLogger).setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @HackerNewsAppScope
    fun provideApiLogger(hackerNewsLogger: HackerNewsLogger) = HttpLoggingInterceptor.Logger {
        hackerNewsLogger.i(TAG, it)
    }
}
