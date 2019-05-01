package com.hacker.news.di.module.api.retrofit

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

@RunWith(MockitoJUnitRunner::class)
class RetrofitCallAdapterFactoryModuleTest {

    @Mock
    lateinit var retrofitCallAdapterFactoryModule: RetrofitCallAdapterFactoryModule

    lateinit var rxJava2CallAdapterFactory: RxJava2CallAdapterFactory

    @Before
    fun initRxJava2CallAdapterFactory() {
        rxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()
    }

    @Test
    fun testProvideRxJava2CallAdapterFactory() {
        `when`(retrofitCallAdapterFactoryModule.provideRxJava2CallAdapterFactory()).thenReturn(rxJava2CallAdapterFactory)
        Assert.assertEquals(
            rxJava2CallAdapterFactory,
            retrofitCallAdapterFactoryModule.provideRxJava2CallAdapterFactory()
        )
    }
}