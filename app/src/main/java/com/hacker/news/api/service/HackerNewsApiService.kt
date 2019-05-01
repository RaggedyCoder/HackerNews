package com.hacker.news.api.service

import com.hacker.news.api.ApiConstants.API_VERSION_V0
import com.hacker.news.api.ApiConstants.ITEMS_ENDPOINT
import com.hacker.news.api.ApiConstants.ITEM_ID_PATH_PARAM
import com.hacker.news.api.ApiConstants.STORIES_ENDPOINT
import com.hacker.news.api.ApiConstants.STORIES_NAME_PATH_PARAM
import com.hacker.news.api.ApiConstants.USERS_ENDPOINT
import com.hacker.news.api.ApiConstants.USER_ID_PATH_PARAM
import com.hacker.news.api.response.ItemInfoResponse
import com.hacker.news.api.response.UserInfoResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface HackerNewsApiService {

    @GET("$API_VERSION_V0$STORIES_ENDPOINT")
    fun getStoriesIDs(@Path(STORIES_NAME_PATH_PARAM) storiesName: String): Single<List<Int>>

    @GET("$API_VERSION_V0$ITEMS_ENDPOINT")
    fun getItemInfo(@Path(ITEM_ID_PATH_PARAM) itemId: String): Call<ItemInfoResponse>

    @GET("$API_VERSION_V0$ITEMS_ENDPOINT")
    fun getItemInfoRxJavaCall(@Path(ITEM_ID_PATH_PARAM) itemId: String): Single<ItemInfoResponse>

    @GET("$API_VERSION_V0$USERS_ENDPOINT")
    fun getUserInfo(@Path(USER_ID_PATH_PARAM) userId: String): Single<UserInfoResponse>
}