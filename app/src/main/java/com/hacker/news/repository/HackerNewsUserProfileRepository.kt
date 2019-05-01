package com.hacker.news.repository

import com.hacker.news.api.response.UserInfoResponse
import com.hacker.news.api.service.HackerNewsApiService
import com.hacker.news.model.*
import javax.inject.Inject


class HackerNewsUserProfileRepository @Inject constructor(
    private val hackerNewsApiService: HackerNewsApiService,
    private val schedulers: RxSchedulers
) {
    fun fetchUserProfile(userId: String, callback: (Result<UserInfoResponse, Error>) -> Unit) {
        val disposable = hackerNewsApiService.getUserInfo(userId)
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.mainThread)
            .subscribe({ response ->
                callback(response.asSuccess())
            }, { error -> callback(error.asError().asFailure()) })
    }
}