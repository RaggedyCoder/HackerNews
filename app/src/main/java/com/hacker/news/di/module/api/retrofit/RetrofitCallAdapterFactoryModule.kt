package com.hacker.news.di.module.api.retrofit

import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_API_RX_JAVA_2_CALL_ADAPTER_FACTORY
import com.hacker.news.di.scope.HackerNewsAppScope
import dagger.Module
import dagger.Provides
import retrofit2.CallAdapter
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Named

@Module
class RetrofitCallAdapterFactoryModule {
    @Provides
    @HackerNewsAppScope
    @Named(QUALIFIER_NAME_API_RX_JAVA_2_CALL_ADAPTER_FACTORY)
    fun provideRxJava2CallAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()
}