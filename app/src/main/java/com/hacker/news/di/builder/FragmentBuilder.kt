package com.hacker.news.di.builder

import com.hacker.news.ui.fragment.item.HackerNewsItemDescriptionFragment
import com.hacker.news.ui.fragment.item.HackerNewsItemListFragment
import com.hacker.news.ui.fragment.profile.HackerNewsUserProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentBuilder {

    @ContributesAndroidInjector
    fun bindHackerNewsItemListFragment(): HackerNewsItemListFragment

    @ContributesAndroidInjector
    fun bindHackerNewsItemDescriptionFragment(): HackerNewsItemDescriptionFragment

    @ContributesAndroidInjector
    fun bindHackerNewsUserProfileFragment(): HackerNewsUserProfileFragment
}