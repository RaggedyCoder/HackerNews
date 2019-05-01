package com.hacker.news.di.builder

import com.hacker.news.ui.activity.HackerNewsLauncherActivity
import com.hacker.news.ui.activity.home.HackerNewsHomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBuilder {
    @ContributesAndroidInjector
    fun bindHackerNewsLauncherActivity(): HackerNewsLauncherActivity

    @ContributesAndroidInjector
    fun bindHackerNewsMainActivity(): HackerNewsHomeActivity
}