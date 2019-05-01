package com.hacker.news.api.service

import com.hacker.news.api.response.UserInfoResponse
import com.hacker.news.getJsonFromFile
import io.reactivex.observers.TestObserver
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.net.InetAddress

@RunWith(JUnit4::class)
class HackerNewsApiServiceUserInfoTest {
    lateinit var hackerNewsApiService: HackerNewsApiService
    lateinit var mockWebServer: MockWebServer

    @Before
    fun initMockServer() {
        mockWebServer = MockWebServer()
        mockWebServer.setDispatcher(object : Dispatcher() {
            override fun dispatch(request: RecordedRequest?): MockResponse {
                return when (request?.path) {
                    "/v0/user/gajju3588.json" -> MockResponse().setResponseCode(200)
                        .setBody(getJsonFromFile("mock-responses/gajju3588.json"))
                    else -> MockResponse().setResponseCode(404)
                }
            }
        })
        mockWebServer.start(InetAddress.getByName("192.168.31.108"), 8080)

        val okHttpClient = OkHttpClient.Builder()
            .build()

        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("http://192.168.31.108:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        hackerNewsApiService = retrofit.create()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testTopStoriesIds() {
        val path = "/v0/user/gajju3588.json"

        val testObserver = TestObserver<UserInfoResponse>()
        hackerNewsApiService.getUserInfo("gajju3588").subscribe(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        Assert.assertEquals(path, mockWebServer.takeRequest().path)
    }

    @Test
    fun testInvalidStory() {
        val path = "/v0/user/invalid.json"
        val testObserver = TestObserver<UserInfoResponse>()
        hackerNewsApiService.getUserInfo("invalid").subscribe(testObserver)
        testObserver.assertError(HttpException::class.java)
        Assert.assertEquals(1, testObserver.errorCount())
        Assert.assertEquals(404, (testObserver.errors()[0] as HttpException).code())
        Assert.assertEquals(path, mockWebServer.takeRequest().path)
    }
}