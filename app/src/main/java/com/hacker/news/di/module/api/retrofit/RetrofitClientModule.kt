package com.hacker.news.di.module.api.retrofit

import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_API_HACKER_NEWS_BASE_URL
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_API_HACKER_NEWS_OK_HTTP_CLIENT
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_API_HACKER_NEWS_RETROFIT_CALLBACK_EXECUTOR
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_API_HACKER_NEWS_RETROFIT_CALLBACK_THREAD_COUNTER
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_API_HACKER_NEWS_RETROFIT_CLIENT
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_API_RX_JAVA_2_CALL_ADAPTER_FACTORY
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_RETROFIT_GSON_CONVERTER_FACTORY
import com.hacker.news.di.module.api.ApiHttpClientModule
import com.hacker.news.di.scope.HackerNewsAppScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Named

@Module(
    includes = [
        RetrofitCallAdapterFactoryModule::class,
        RetrofitConverterFactoryModule::class,
        ApiHttpClientModule::class
    ]
)
class RetrofitClientModule {

    @Provides
    @HackerNewsAppScope
    @Named(QUALIFIER_NAME_API_HACKER_NEWS_RETROFIT_CLIENT)
    fun provideRetrofitClient(
        @Named(QUALIFIER_NAME_API_HACKER_NEWS_BASE_URL) baseUrl: String,
        @Named(QUALIFIER_NAME_API_HACKER_NEWS_OK_HTTP_CLIENT) okHttpClient: OkHttpClient,
        @Named(QUALIFIER_NAME_API_HACKER_NEWS_RETROFIT_CALLBACK_EXECUTOR) callbackExecutor: Executor,
        @Named(QUALIFIER_NAME_RETROFIT_GSON_CONVERTER_FACTORY) gsonConverterFactory: Converter.Factory,
        @Named(QUALIFIER_NAME_API_RX_JAVA_2_CALL_ADAPTER_FACTORY) rxJava2CallAdapterFactory: CallAdapter.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .callbackExecutor(callbackExecutor)
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(rxJava2CallAdapterFactory)
        .build()

    @Provides
    @HackerNewsAppScope
    @Named(QUALIFIER_NAME_API_HACKER_NEWS_RETROFIT_CALLBACK_EXECUTOR)
    fun provideRetrofitCallbackExecutor(
        @Named(QUALIFIER_NAME_API_HACKER_NEWS_RETROFIT_CALLBACK_THREAD_COUNTER) threadCounter: AtomicInteger
    ): Executor = Executors.newCachedThreadPool {
        Thread(it, "Hacker-News-Api-Callback-Thread-${threadCounter.getAndIncrement()}").apply {
            this.isDaemon = true
        }
    }

    @Provides
    @HackerNewsAppScope
    @Named(QUALIFIER_NAME_API_HACKER_NEWS_RETROFIT_CALLBACK_THREAD_COUNTER)
    fun provideRetrofitCallbackExecutorThreadCounter() = AtomicInteger()
}
