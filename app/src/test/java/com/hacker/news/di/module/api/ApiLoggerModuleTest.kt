package com.hacker.news.di.module.api

import com.hacker.news.util.HackerNewsLogger
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ApiLoggerModuleTest {

    @Mock
    lateinit var apiLoggerModule: ApiLoggerModule

    @Mock
    lateinit var httpLogger: HttpLoggingInterceptor.Logger

    @Mock
    lateinit var hackerNewsLogger: HackerNewsLogger


    @Test
    fun testProvideHttpLoggingInterceptor() {
        val actualResult = HttpLoggingInterceptor(httpLogger)
        `when`(apiLoggerModule.provideHttpLoggingInterceptor(httpLogger)).thenReturn(actualResult)
        Assert.assertEquals(actualResult, apiLoggerModule.provideHttpLoggingInterceptor(httpLogger))
    }

    @Test
    fun testProvideApiLogger() {
        val actualResult = HttpLoggingInterceptor.Logger {

        }

        `when`(apiLoggerModule.provideApiLogger(hackerNewsLogger)).thenReturn(actualResult)
        Assert.assertEquals(actualResult, apiLoggerModule.provideApiLogger(hackerNewsLogger))
    }
}