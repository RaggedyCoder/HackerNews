package com.hacker.news.di.builder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hacker.news.di.key.ViewModelKey
import com.hacker.news.viewmodel.HackerNewsHomeViewModel
import com.hacker.news.viewmodel.HackerNewsLauncherViewModel
import com.hacker.news.viewmodel.factory.HackerNewsViewModelFactory
import com.hacker.news.viewmodel.item.HackerNewsItemDescriptionViewModel
import com.hacker.news.viewmodel.item.HackerNewsItemListViewModel
import com.hacker.news.viewmodel.profile.HackerNewsUserProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelBuilder {

    @Binds
    internal abstract fun bindViewModelFactory(factory: HackerNewsViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HackerNewsLauncherViewModel::class)
    internal abstract fun bindHackerNewsLauncherViewModel(viewModel: HackerNewsLauncherViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HackerNewsHomeViewModel::class)
    internal abstract fun bindHackerNewsMainViewModel(viewModel: HackerNewsHomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HackerNewsItemListViewModel::class)
    internal abstract fun bindHackerNewsItemListViewModel(viewModel: HackerNewsItemListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HackerNewsItemDescriptionViewModel::class)
    internal abstract fun bindHackerNewsItemDescriptionViewModel(viewModel: HackerNewsItemDescriptionViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(HackerNewsUserProfileViewModel::class)
    internal abstract fun bindHackerNewsUserProfileViewModel(viewModel: HackerNewsUserProfileViewModel): ViewModel

}