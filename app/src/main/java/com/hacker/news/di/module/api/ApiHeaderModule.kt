package com.hacker.news.di.module.api

import com.hacker.news.api.ApiConstants.Headers.ACCEPT
import com.hacker.news.api.ApiConstants.Headers.CONTENT_TYPE
import com.hacker.news.api.ApiConstants.Headers.CONTENT_TYPE_JSON
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_API_HEADER_INTERCEPTOR
import com.hacker.news.di.scope.HackerNewsAppScope
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import javax.inject.Named

@Module
class ApiHeaderModule {

    @Provides
    @HackerNewsAppScope
    @Named(QUALIFIER_NAME_API_HEADER_INTERCEPTOR)
    fun provideOpenApiHeaderInterceptor(): Interceptor = Interceptor {
        val newRequest = it.request().newBuilder()
            .addHeader(ACCEPT, CONTENT_TYPE_JSON)
            .addHeader(CONTENT_TYPE, CONTENT_TYPE_JSON)
            .build()
        return@Interceptor it.proceed(newRequest)
    }
}