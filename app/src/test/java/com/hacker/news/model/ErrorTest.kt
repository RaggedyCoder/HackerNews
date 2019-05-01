package com.hacker.news.model

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ErrorTest {

    private val dummyStatusCode = 404
    private val dummyResponseBody = "dummy network response"
    private val dummyReason = "dummy network error"
    private val dummyThrowable = Throwable(dummyReason)
    @Test
    fun testNetworkError() {
        val expected = NetworkError(dummyReason)
        val actual = Error.network(dummyReason)
        Assert.assertTrue(actual is NetworkError)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun testResponseError() {
        val expected = ResponseError(dummyStatusCode, dummyResponseBody, dummyReason)
        val actual = Error.response(dummyStatusCode, dummyResponseBody, dummyReason)
        Assert.assertTrue(actual is ResponseError)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun testOtherError() {
        val expected = OtherError(dummyReason, dummyThrowable)
        val actual = Error.other(dummyThrowable)
        Assert.assertTrue(actual is OtherError)
        Assert.assertEquals(expected, actual)
    }
}