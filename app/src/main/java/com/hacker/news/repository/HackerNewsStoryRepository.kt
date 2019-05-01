package com.hacker.news.repository

import com.hacker.news.api.service.HackerNewsApiService
import com.hacker.news.model.*
import com.hacker.news.util.Story
import javax.inject.Inject


class HackerNewsStoryRepository @Inject constructor(
    private val hackerNewsApiService: HackerNewsApiService,
    private val schedulers: RxSchedulers
) {
    fun fetchStoryIds(story: Story, callback: (Result<List<Int>, Error>) -> Unit) {
        val disposable = hackerNewsApiService.getStoriesIDs(story.storyPathParam)
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.mainThread)
            .subscribe({ response ->
                callback(response.asSuccess())
            }, { error -> callback(error.asError().asFailure()) })
    }
}