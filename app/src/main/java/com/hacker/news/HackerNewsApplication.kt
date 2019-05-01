package com.hacker.news

import com.hacker.news.di.component.DaggerHackerNewsApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class HackerNewsApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerHackerNewsApplicationComponent.builder().create(this)
    }
}