package com.hacker.news.api.service

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
class HackerNewsApiServiceStoriesIDsTest {
    lateinit var hackerNewsApiService: HackerNewsApiService
    lateinit var mockWebServer: MockWebServer

    @Before
    fun initMockServer() {
        mockWebServer = MockWebServer()
        mockWebServer.setDispatcher(object : Dispatcher() {
            override fun dispatch(request: RecordedRequest?): MockResponse {
                when (request?.path) {
                    "/v0/topstories.json" -> return MockResponse().setResponseCode(200)
                        .setBody(getJsonFromFile("mock-responses/topstories.json"))
                    "/v0/newstories.json" -> return MockResponse().setResponseCode(200)
                        .setBody(getJsonFromFile("mock-responses/newstories.json"))
                    "/v0/beststories.json" -> return MockResponse().setResponseCode(200)
                        .setBody(getJsonFromFile("mock-responses/beststories.json"))
                    "/v0/askstories.json" -> return MockResponse().setResponseCode(200)
                        .setBody(getJsonFromFile("mock-responses/askstories.json"))
                    "/v0/showstories.json" -> return MockResponse().setResponseCode(200)
                        .setBody(getJsonFromFile("mock-responses/showstories.json"))
                    "/v0/jobstories.json" -> return MockResponse().setResponseCode(200)
                        .setBody(getJsonFromFile("mock-responses/jobstories.json"))
                    else -> return MockResponse().setResponseCode(401)
                        .setBody(getJsonFromFile("mock-responses/unathorized.json"))
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
        val path = "/v0/topstories.json"

        val testObserver = TestObserver<List<Int>>()
        hackerNewsApiService.getStoriesIDs("topstories").subscribe(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        Assert.assertEquals(path, mockWebServer.takeRequest().path)
    }

    @Test
    fun testNewStoriesIds() {
        val path = "/v0/newstories.json"

        val testObserver = TestObserver<List<Int>>()
        hackerNewsApiService.getStoriesIDs("newstories").subscribe(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        Assert.assertEquals(path, mockWebServer.takeRequest().path)
    }

    @Test
    fun testBestStoriesIds() {
        val path = "/v0/beststories.json"

        val testObserver = TestObserver<List<Int>>()
        hackerNewsApiService.getStoriesIDs("beststories").subscribe(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        Assert.assertEquals(path, mockWebServer.takeRequest().path)
    }

    @Test
    fun testAskStoriesIds() {
        val path = "/v0/askstories.json"

        val testObserver = TestObserver<List<Int>>()
        hackerNewsApiService.getStoriesIDs("askstories").subscribe(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        Assert.assertEquals(path, mockWebServer.takeRequest().path)
    }

    @Test
    fun testShowStoriesIds() {
        val path = "/v0/showstories.json"

        val testObserver = TestObserver<List<Int>>()
        hackerNewsApiService.getStoriesIDs("showstories").subscribe(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        Assert.assertEquals(path, mockWebServer.takeRequest().path)
    }

    @Test
    fun testJobStoriesIds() {
        val path = "/v0/jobstories.json"

        val testObserver = TestObserver<List<Int>>()
        hackerNewsApiService.getStoriesIDs("jobstories").subscribe(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        Assert.assertEquals(path, mockWebServer.takeRequest().path)
    }

    @Test
    fun testInvalidStory() {
        val path = "/v0/invalid.json"
        val testObserver = TestObserver<List<Int>>()
        hackerNewsApiService.getStoriesIDs("invalid").subscribe(testObserver)
        testObserver.assertError(HttpException::class.java)
        Assert.assertEquals(1, testObserver.errorCount())
        Assert.assertEquals(path, mockWebServer.takeRequest().path)
    }
}