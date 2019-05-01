package com.hacker.news.di.module.api.retrofit

import com.hacker.news.api.service.HackerNewsApiService
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit

@RunWith(MockitoJUnitRunner::class)
class RetrofitApiServiceModuleTest {

    @Mock
    lateinit var retrofit: Retrofit

    @Mock
    lateinit var retrofitApiServiceModule: RetrofitApiServiceModule

    @Test
    fun testProvideHackerNewsApiService() {
        val actualResult = Mockito.mock(HackerNewsApiService::class.java)
        `when`(retrofitApiServiceModule.provideHackerNewsApiService(retrofit)).thenReturn(actualResult)

        Assert.assertEquals(actualResult, retrofitApiServiceModule.provideHackerNewsApiService(retrofit))
    }
}