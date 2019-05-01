package com.hacker.news.di.module.api.retrofit

import com.google.gson.Gson
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_API_GSON_PARSER
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_RETROFIT_GSON_CONVERTER_FACTORY
import com.hacker.news.di.scope.HackerNewsAppScope
import dagger.Module
import dagger.Provides
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
class RetrofitConverterFactoryModule {
    @Provides
    @HackerNewsAppScope
    @Named(QUALIFIER_NAME_RETROFIT_GSON_CONVERTER_FACTORY)
    fun provideGsonConverterFactory(@Named(QUALIFIER_NAME_API_GSON_PARSER) gson: Gson): Converter.Factory =
        GsonConverterFactory.create(gson)
}