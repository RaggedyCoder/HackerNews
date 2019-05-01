package com.hacker.news.di.module.api

import com.hacker.news.HackerNewsApplication
import okhttp3.Cache
import okhttp3.Interceptor
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@RunWith(MockitoJUnitRunner::class)
class ApiCacheModuleTest {

    @Mock
    lateinit var apiCacheModule: ApiCacheModule

    @Mock
    lateinit var hackerNewsApplication: HackerNewsApplication

    @Test
    fun testProvideCacheInterceptor() {
        val actualResult = Interceptor {
            return@Interceptor it.proceed(it.request())
        }
        `when`(apiCacheModule.provideCacheInterceptor(hackerNewsApplication, 10, 10))
            .thenReturn(actualResult)
        Assert.assertEquals(
            actualResult,
            apiCacheModule.provideCacheInterceptor(hackerNewsApplication, 10, 10)
        )
    }

    @Test
    fun testProvideCache() {
        val actualResult = Cache(File(""), 10 * 1024 * 1024)
        `when`(apiCacheModule.provideCache(10 * 1024 * 1024, File("")))
            .thenReturn(actualResult)
        Assert.assertEquals(
            actualResult,
            apiCacheModule.provideCache(10 * 1024 * 1024, File(""))
        )
    }

    @Test
    fun testProvideHttpCacheDirectoryFile() {
        val actualResult = File("", "http-cache")
        `when`(apiCacheModule.provideHttpCacheDirectoryFile(hackerNewsApplication, "http-cache"))
            .thenReturn(actualResult)
        Assert.assertEquals(
            actualResult,
            apiCacheModule.provideHttpCacheDirectoryFile(hackerNewsApplication, "http-cache")
        )
    }

    @Test
    fun testProvideHttpCacheSize() {
        `when`(apiCacheModule.provideHttpCacheSize()).thenCallRealMethod()
        Assert.assertEquals(10 * 1024 * 1024, apiCacheModule.provideHttpCacheSize())
    }

    @Test
    fun testProvideHttpCacheChildPath() {
        `when`(apiCacheModule.provideHttpCacheChildPath()).thenCallRealMethod()
        Assert.assertEquals("http-cache", apiCacheModule.provideHttpCacheChildPath())

    }
}