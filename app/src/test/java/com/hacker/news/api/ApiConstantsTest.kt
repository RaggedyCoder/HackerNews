package com.hacker.news.api

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ApiConstantsTest {

    @Test
    fun testApiConstantsStoriesNamePathParam() {
        Assert.assertEquals("storiesName", ApiConstants.STORIES_NAME_PATH_PARAM)
    }

    @Test
    fun testApiConstantsItemIdPathParam() {
        Assert.assertEquals("itemId", ApiConstants.ITEM_ID_PATH_PARAM)
    }

    @Test
    fun testApiConstantsUserIdPathParam() {
        Assert.assertEquals("userId", ApiConstants.USER_ID_PATH_PARAM)
    }

    @Test
    fun testApiConstantHackerNewsBaseUrl() {
        Assert.assertEquals("https://hacker-news.firebaseio.com", ApiConstants.HACKER_NEWS_BASE_URL)
    }

    @Test
    fun testApiConstantsApiVersion0() {
        Assert.assertEquals("/v0", ApiConstants.API_VERSION_V0)
    }

    @Test
    fun testApiConstantsStoriesEndpoint() {
        Assert.assertEquals("/{storiesName}.json", ApiConstants.STORIES_ENDPOINT)
    }

    @Test
    fun testApiConstantsItemsEndpoint() {
        Assert.assertEquals("/item/{itemId}.json", ApiConstants.ITEMS_ENDPOINT)
    }

    @Test
    fun testApiConstantsUsersEndpoint() {
        Assert.assertEquals("/user/{userId}.json", ApiConstants.USERS_ENDPOINT)
    }

    @Test
    fun testApiConstantsHeadersAccept() {
        Assert.assertEquals("Accept", ApiConstants.Headers.ACCEPT)
    }

    @Test
    fun testApiConstantsHeadersContentType() {
        Assert.assertEquals("Content-Type", ApiConstants.Headers.CONTENT_TYPE)
    }

    @Test
    fun testApiConstantsHeadersContentTypeJson() {
        Assert.assertEquals("application/json", ApiConstants.Headers.CONTENT_TYPE_JSON)
    }
}