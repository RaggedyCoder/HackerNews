package com.hacker.news.di.module.api.retrofit

import com.hacker.news.api.service.HackerNewsApiService
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_API_HACKER_NEWS_RETROFIT_CLIENT
import com.hacker.news.di.scope.HackerNewsAppScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Named

@Module(includes = [RetrofitClientModule::class])
class RetrofitApiServiceModule {

    @Provides
    @HackerNewsAppScope
    fun provideHackerNewsApiService(
        @Named(QUALIFIER_NAME_API_HACKER_NEWS_RETROFIT_CLIENT) retrofit: Retrofit
    ): HackerNewsApiService = retrofit.create()
}