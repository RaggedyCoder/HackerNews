package com.hacker.news.di.module.api

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ApiUtilityModuleTest {

    @Mock
    lateinit var apiUtilityModule: ApiUtilityModule

    @Test
    fun testProvideReadTimeout() {
        `when`(apiUtilityModule.provideReadTimeout()).thenCallRealMethod()
        Assert.assertEquals(60, apiUtilityModule.provideReadTimeout())
    }

    @Test
    fun testProvideConnectTimeout() {
        `when`(apiUtilityModule.provideConnectTimeout()).thenCallRealMethod()
        Assert.assertEquals(60, apiUtilityModule.provideConnectTimeout())
    }

    @Test
    fun testProvideApiCacheMaxAge() {
        `when`(apiUtilityModule.provideApiCacheMaxAge()).thenCallRealMethod()
        Assert.assertEquals(30, apiUtilityModule.provideApiCacheMaxAge())
    }

    @Test
    fun testProvideApiCacheMaxStale() {
        `when`(apiUtilityModule.provideApiCacheMaxStale()).thenCallRealMethod()
        Assert.assertEquals(86400, apiUtilityModule.provideApiCacheMaxStale())
    }

    @Test
    fun testProvideHackerNewsBaseUrl() {
        `when`(apiUtilityModule.provideHackerNewsBaseUrl()).thenCallRealMethod()
        Assert.assertEquals("https://hacker-news.firebaseio.com", apiUtilityModule.provideHackerNewsBaseUrl())
    }
}