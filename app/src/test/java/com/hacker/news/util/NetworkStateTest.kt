package com.hacker.news.util

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NetworkStateTest {

    @Test
    fun testRefreshingState() {
        Assert.assertEquals(Status.REFRESH, NetworkState.REFRESHING.status)
    }

    @Test
    fun testLoadingState() {
        Assert.assertEquals(Status.RUNNING, NetworkState.LOADING.status)
    }

    @Test
    fun testLoadedState() {
        Assert.assertEquals(Status.SUCCESS, NetworkState.LOADED.status)
    }

    @Test
    fun testCompletedState() {
        Assert.assertEquals(Status.COMPLETED, NetworkState.COMPLETED.status)
    }

    @Test
    fun testErrorState() {
        val result = NetworkState.error(Exception())
        Assert.assertEquals(Status.FAILED, result.status)
    }
}