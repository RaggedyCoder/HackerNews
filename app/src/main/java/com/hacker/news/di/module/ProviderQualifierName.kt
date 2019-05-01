package com.hacker.news.di.module

object ProviderQualifierName {
    const val QUALIFIER_NAME_HTTP_CACHE_MAX_SIZE = "httpMaxCacheSize"
    const val QUALIFIER_NAME_HTTP_CACHE_FOLDER_NAME = "httpCacheFolderName"
    const val QUALIFIER_NAME_HTTP_CACHE_DIRECTORY_FILE = "httpCacheDirectoryFile"

    const val QUALIFIER_NAME_HTTP_READ_TIMEOUT = "httpReadTimeOut"
    const val QUALIFIER_NAME_HTTP_CONNECT_TIMEOUT = "httpConnectTimeOut"

    const val QUALIFIER_NAME_API_CACHE_MAX_AGE = "apiCacheMaxAge"
    const val QUALIFIER_NAME_API_CACHE_MAX_STALE = "apiCacheMaxStale"

    const val QUALIFIER_NAME_API_HEADER_INTERCEPTOR = "headerInterceptor"
    const val QUALIFIER_NAME_API_CACHE_INTERCEPTOR = "cacheInterceptor"
    const val QUALIFIER_NAME_API_LOGGING_INTERCEPTOR = "loggingInterceptor"

    const val QUALIFIER_NAME_API_HACKER_NEWS_BASE_URL = "hackerNewsBaseUrl"

    const val QUALIFIER_NAME_RETROFIT_GSON_CONVERTER_FACTORY = "retrofitGsonConverterFactory"
    const val QUALIFIER_NAME_API_RX_JAVA_2_CALL_ADAPTER_FACTORY = "retrofitRxJava2CallAdapterFactory"

    const val QUALIFIER_NAME_API_HACKER_NEWS_OK_HTTP_CLIENT = "apiHackerNewsOkHttpClient"

    const val QUALIFIER_NAME_API_HACKER_NEWS_RETROFIT_CLIENT = "apiHackerNewsRetrofitClient"

    const val QUALIFIER_NAME_API_HACKER_NEWS_RETROFIT_CALLBACK_EXECUTOR = "apiHackerNewsRetrofitCallbackExecutor"

    const val QUALIFIER_NAME_API_HACKER_NEWS_RETROFIT_CALLBACK_THREAD_COUNTER =
        "apiHackerNewsRetrofitCallbackThreadCounter"

    const val QUALIFIER_NAME_API_GSON_PARSER = "apiGsonParser"

    const val QUALIFIER_NAME_DATA_SOURCE_PAGE_SIZE = "dataSourcePageSize"

    const val QUALIFIER_NAME_DATA_SOURCE_NETWORK_EXECUTOR = "dataSourceNetworkExecutor"

    const val QUALIFIER_NAME_DEFAULT_NUMBER_FORMATTER = "defaultNumberFormatter"
}