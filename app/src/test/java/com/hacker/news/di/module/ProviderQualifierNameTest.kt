package com.hacker.news.di.module

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProviderQualifierNameTest {
    @Test
    fun testQualifierNameHttpCacheMaxSize() {
        Assert.assertEquals("httpMaxCacheSize", ProviderQualifierName.QUALIFIER_NAME_HTTP_CACHE_MAX_SIZE)
    }

    @Test
    fun testQualifierNameHttpCacheFolderName() {
        Assert.assertEquals("httpCacheFolderName", ProviderQualifierName.QUALIFIER_NAME_HTTP_CACHE_FOLDER_NAME)
    }

    @Test
    fun testQualifierNameHttpCacheDirectory() {
        Assert.assertEquals("httpCacheDirectoryFile", ProviderQualifierName.QUALIFIER_NAME_HTTP_CACHE_DIRECTORY_FILE)
    }

    @Test
    fun testQualifierNameHttpReadTimeout() {
        Assert.assertEquals("httpReadTimeOut", ProviderQualifierName.QUALIFIER_NAME_HTTP_READ_TIMEOUT)
    }

    @Test
    fun testQualifierNameHttpConnectTimeOut() {
        Assert.assertEquals("httpConnectTimeOut", ProviderQualifierName.QUALIFIER_NAME_HTTP_CONNECT_TIMEOUT)
    }

    @Test
    fun testQualifierNameApiCacheMaxAge() {
        Assert.assertEquals("apiCacheMaxAge", ProviderQualifierName.QUALIFIER_NAME_API_CACHE_MAX_AGE)
    }

    @Test
    fun testQualifierNameApiCacheMaxState() {
        Assert.assertEquals("apiCacheMaxStale", ProviderQualifierName.QUALIFIER_NAME_API_CACHE_MAX_STALE)
    }

    @Test
    fun testQualifierNameApiHeaderInterceptor() {
        Assert.assertEquals("headerInterceptor", ProviderQualifierName.QUALIFIER_NAME_API_HEADER_INTERCEPTOR)
    }

    @Test
    fun testQualifierNameApiCacheInterceptor() {
        Assert.assertEquals("cacheInterceptor", ProviderQualifierName.QUALIFIER_NAME_API_CACHE_INTERCEPTOR)
    }

    @Test
    fun testQualifierNameApiLoggingInterceptor() {
        Assert.assertEquals("loggingInterceptor", ProviderQualifierName.QUALIFIER_NAME_API_LOGGING_INTERCEPTOR)
    }

    @Test
    fun testQualifierNameApiHackerNewsBaseUrl() {
        Assert.assertEquals("hackerNewsBaseUrl", ProviderQualifierName.QUALIFIER_NAME_API_HACKER_NEWS_BASE_URL)
    }

    @Test
    fun testQualifierNameApiRetrofitGsonConverterFactory() {
        Assert.assertEquals(
            "retrofitGsonConverterFactory",
            ProviderQualifierName.QUALIFIER_NAME_RETROFIT_GSON_CONVERTER_FACTORY
        )
    }

    @Test
    fun testQualifierNameRxJava2CallAdapterFactory() {
        Assert.assertEquals(
            "retrofitRxJava2CallAdapterFactory",
            ProviderQualifierName.QUALIFIER_NAME_API_RX_JAVA_2_CALL_ADAPTER_FACTORY
        )
    }

    @Test
    fun testQualifierNameApiOkHttpClient() {
        Assert.assertEquals(
            "apiHackerNewsOkHttpClient",
            ProviderQualifierName.QUALIFIER_NAME_API_HACKER_NEWS_OK_HTTP_CLIENT
        )
    }

    @Test
    fun testQualifierNameApiHackerNewsRetrofitClient() {
        Assert.assertEquals(
            "apiHackerNewsRetrofitClient",
            ProviderQualifierName.QUALIFIER_NAME_API_HACKER_NEWS_RETROFIT_CLIENT
        )
    }

    @Test
    fun testQualifierNameApiHackerNewsRetrofitCallbackExecutor() {
        Assert.assertEquals(
            "apiHackerNewsRetrofitCallbackExecutor",
            ProviderQualifierName.QUALIFIER_NAME_API_HACKER_NEWS_RETROFIT_CALLBACK_EXECUTOR
        )
    }

    @Test
    fun testQualifierNameApiHackerNewsRetrofitCallbackThreadCounter() {
        Assert.assertEquals(
            "apiHackerNewsRetrofitCallbackThreadCounter",
            ProviderQualifierName.QUALIFIER_NAME_API_HACKER_NEWS_RETROFIT_CALLBACK_THREAD_COUNTER
        )
    }

    @Test
    fun testQualifierNameApiGsonParser() {
        Assert.assertEquals("apiGsonParser", ProviderQualifierName.QUALIFIER_NAME_API_GSON_PARSER)
    }

    @Test
    fun testQualifierNameDataSourcePageSize() {
        Assert.assertEquals("dataSourcePageSize", ProviderQualifierName.QUALIFIER_NAME_DATA_SOURCE_PAGE_SIZE)
    }

    @Test
    fun testQualifierDataSourceNetworkExecutor() {
        Assert.assertEquals(
            "dataSourceNetworkExecutor",
            ProviderQualifierName.QUALIFIER_NAME_DATA_SOURCE_NETWORK_EXECUTOR
        )
    }

    @Test
    fun testQualifierDefaultNumberFormatter() {
        Assert.assertEquals("defaultNumberFormatter", ProviderQualifierName.QUALIFIER_NAME_DEFAULT_NUMBER_FORMATTER)
    }
}