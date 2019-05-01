package com.hacker.news.di.component

import com.hacker.news.HackerNewsApplication
import com.hacker.news.di.builder.ActivityBuilder
import com.hacker.news.di.builder.FragmentBuilder
import com.hacker.news.di.builder.ViewModelBuilder
import com.hacker.news.di.module.HackerNewsApplicationModule
import com.hacker.news.di.module.api.retrofit.RetrofitApiServiceModule
import com.hacker.news.di.module.datasource.fetcher.DataSourceFetcherModule
import com.hacker.news.di.scope.HackerNewsAppScope
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@Component(
    modules = [
        AndroidInjectionModule::class,
        ViewModelBuilder::class,
        HackerNewsApplicationModule::class,
        ActivityBuilder::class,
        FragmentBuilder::class,
        DataSourceFetcherModule::class,
        RetrofitApiServiceModule::class
    ]
)
@HackerNewsAppScope
interface HackerNewsApplicationComponent : AndroidInjector<HackerNewsApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<HackerNewsApplication>()
}