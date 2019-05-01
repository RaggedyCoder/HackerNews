package com.hacker.news.viewmodel

import android.app.Application
import android.content.Intent
import com.hacker.news.ui.activity.home.HackerNewsHomeActivity
import javax.inject.Inject

class HackerNewsLauncherViewModel @Inject constructor(application: Application) :
    HackerNewsBaseViewModel(application) {

    fun performLaunchAction() {
        startActivity(
            Intent(getApplication(), HackerNewsHomeActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
            true
        )
    }
}