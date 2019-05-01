package com.hacker.news.viewmodel

import android.app.Application
import com.hacker.news.R
import com.hacker.news.util.Story
import javax.inject.Inject

class HackerNewsHomeViewModel @Inject constructor(application: Application) :
    HackerNewsBaseViewModel(application) {

    fun getStoryNameFromNavigationMenuId(navigationMenuItemId: Int) = when (navigationMenuItemId) {
        R.id.menu_top_stories -> Story.TOP
        R.id.menu_new_stories -> Story.NEW
        R.id.menu_best_stories -> Story.BEST
        R.id.menu_ask_stories -> Story.ASK
        R.id.menu_show_stories -> Story.SHOW
        R.id.menu_job_stories -> Story.JOB
        else ->  Story.TOP
    }
}