package com.hacker.news.api

import com.hacker.news.BuildConfig

object ApiConstants {


    const val STORIES_NAME_PATH_PARAM = "storiesName"

    const val ITEM_ID_PATH_PARAM = "itemId"

    const val USER_ID_PATH_PARAM = "userId"

    const val HACKER_NEWS_BASE_URL = BuildConfig.HACKER_NEWS_BASE_URL

    const val API_VERSION_V0 = "/v0"

    const val STORIES_ENDPOINT = "/{$STORIES_NAME_PATH_PARAM}.json"

    const val ITEMS_ENDPOINT = "/item/{$ITEM_ID_PATH_PARAM}.json"

    const val USERS_ENDPOINT = "/user/{$USER_ID_PATH_PARAM}.json"

    object Headers {
        const val ACCEPT = "Accept"
        const val CONTENT_TYPE = "Content-Type"

        const val CONTENT_TYPE_JSON = "application/json"
    }
}