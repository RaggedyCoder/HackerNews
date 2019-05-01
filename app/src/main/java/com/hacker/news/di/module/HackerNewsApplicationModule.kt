package com.hacker.news.di.module

import android.app.Application
import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hacker.news.HackerNewsApplication
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_API_GSON_PARSER
import com.hacker.news.di.module.ProviderQualifierName.QUALIFIER_NAME_DEFAULT_NUMBER_FORMATTER
import com.hacker.news.di.scope.HackerNewsAppScope
import com.hacker.news.model.RxSchedulers
import com.hacker.news.util.HackerNewsDateDeserializer
import com.hacker.news.util.HackerNewsDateSerializer
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.NumberFormat
import java.util.*
import javax.inject.Named

@Module
class HackerNewsApplicationModule {

    @Provides
    fun provideContext(hackerNewsApplication: HackerNewsApplication): Context = hackerNewsApplication

    @Provides
    fun provideApplication(hackerNewsApplication: HackerNewsApplication): Application = hackerNewsApplication

    @Provides
    @HackerNewsAppScope
    @Named(QUALIFIER_NAME_DEFAULT_NUMBER_FORMATTER)
    fun provideScoreNumberFormatter() = NumberFormat.getNumberInstance()

    @Provides
    @HackerNewsAppScope
    fun provideRxSchedulers(): RxSchedulers = RxSchedulers(
        io = Schedulers.io(),
        computation = Schedulers.computation(),
        mainThread = AndroidSchedulers.mainThread()
    )

    @Provides
    @HackerNewsAppScope
    @Named(QUALIFIER_NAME_API_GSON_PARSER)
    fun provideApiSerializerGson(
        dateDeserializer: HackerNewsDateDeserializer,
        dateSerializer: HackerNewsDateSerializer
    ): Gson =
        GsonBuilder()
            .enableComplexMapKeySerialization()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .registerTypeAdapter(Date::class.java, dateDeserializer)
            .registerTypeAdapter(Date::class.java, dateSerializer)
            .setPrettyPrinting()
            .create()
}