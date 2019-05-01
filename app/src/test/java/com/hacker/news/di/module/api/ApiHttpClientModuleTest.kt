package com.hacker.news.di.module.api

import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class ApiHttpClientModuleTest {

    @Mock
    lateinit var apiHttpClientModule: ApiHttpClientModule

    @Mock
    lateinit var cache: Cache

    val readTimeOut: Long = 60

    val connectTimeOut: Long = 60

    @Mock
    lateinit var httpHeaderInterceptor: Interceptor

    @Mock
    lateinit var cacheInterceptor: Interceptor

    @Mock
    lateinit var httpLoggerInterceptor: Interceptor

    lateinit var okHttpClient: OkHttpClient

    @Before
    fun initOkHttpClient() {
        okHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .readTimeout(readTimeOut, TimeUnit.SECONDS)
            .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
            .addInterceptor(httpLoggerInterceptor)
            .addNetworkInterceptor(cacheInterceptor)
            .addNetworkInterceptor(httpHeaderInterceptor)
            .build()
    }

    @Test
    fun testProvideApiOkHttpClient() {
        `when`(
            apiHttpClientModule.provideApiOkHttpClient(
                cache,
                readTimeOut,
                connectTimeOut,
                httpHeaderInterceptor,
                cacheInterceptor,
                httpLoggerInterceptor
            )
        ).thenReturn(okHttpClient)

        Assert.assertEquals(
            okHttpClient, apiHttpClientModule.provideApiOkHttpClient(
                cache,
                readTimeOut,
                connectTimeOut,
                httpHeaderInterceptor,
                cacheInterceptor,
                httpLoggerInterceptor
            )
        )
    }

    @Test
    fun testProvideApiOkHttpClientReadTimeOut() {
        Assert.assertEquals((readTimeOut * 1000).toInt(), okHttpClient.readTimeoutMillis())
    }

    @Test
    fun testProvideApiOkHttpClientConnectTimeOut() {
        Assert.assertEquals((connectTimeOut * 1000).toInt(), okHttpClient.connectTimeoutMillis())
    }
}