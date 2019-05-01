package com.hacker.news.model

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ResultTest {

    @Test
    fun testSuccess() {
        val dummy = Any()
        val actual: Success<Any, Error> = Success(dummy)
        val expected = Result.success<Any, Error>(dummy)
        Assert.assertTrue(expected is Success)
        Assert.assertEquals(actual.value, (expected as Success).value)
    }

    @Test
    fun testFailure() {
        val dummy = NetworkError("dummy error")
        val actual: Failure<Any, Error> = Failure(dummy)
        val expected = Result.failure<Any, Error>(dummy)
        Assert.assertTrue(expected is Failure)
        Assert.assertEquals(actual.error.reason, (expected as Failure).error.reason)
    }
}