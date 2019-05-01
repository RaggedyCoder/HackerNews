package com.hacker.news.di.module.api.retrofit

import com.google.gson.Gson
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(MockitoJUnitRunner::class)
class RetrofitConverterFactoryModuleTest {

    @Mock
    lateinit var retrofitConverterFactoryModule: RetrofitConverterFactoryModule

    @Mock
    lateinit var gson: Gson

    lateinit var gsonConverterFactory: GsonConverterFactory

    @Before
    fun initGsonConverterFactory() {
        gsonConverterFactory = GsonConverterFactory.create(gson)
    }

    @Test
    fun testProvideGsonConverterFactory() {
        `when`(retrofitConverterFactoryModule.provideGsonConverterFactory(gson)).thenReturn(gsonConverterFactory)

        Assert.assertEquals(gsonConverterFactory, retrofitConverterFactoryModule.provideGsonConverterFactory(gson))
    }
}