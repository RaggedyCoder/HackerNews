package com.hacker.news.ui.activity

import android.os.Bundle
import com.hacker.news.viewmodel.HackerNewsLauncherViewModel
import dagger.android.AndroidInjection

class HackerNewsLauncherActivity : HackerNewsBaseActivity() {

    override fun getViewModelClass() = HackerNewsLauncherViewModel::class.java

    private val hackerNewsLauncherViewModel by lazy {
        getViewModel() as HackerNewsLauncherViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        hackerNewsLauncherViewModel.performLaunchAction()
    }
}