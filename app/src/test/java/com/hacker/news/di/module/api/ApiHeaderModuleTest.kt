package com.hacker.news.di.module.api

import okhttp3.Interceptor
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ApiHeaderModuleTest {

    @Mock
    lateinit var apiHeaderModule: ApiHeaderModule

    @Test
    fun testProvideOpenApiHeaderInterceptor() {
        val actualResult = Interceptor {
            return@Interceptor it.proceed(it.request())
        }

        `when`(apiHeaderModule.provideOpenApiHeaderInterceptor()).thenReturn(actualResult)

        Assert.assertEquals(actualResult, apiHeaderModule.provideOpenApiHeaderInterceptor())
    }
}