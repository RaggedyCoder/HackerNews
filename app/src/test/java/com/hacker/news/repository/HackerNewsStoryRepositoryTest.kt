package com.hacker.news.repository

import com.hacker.news.api.service.HackerNewsApiService
import com.hacker.news.getJsonFromFile
import com.hacker.news.model.RxSchedulers
import com.hacker.news.model.Success
import com.hacker.news.util.Story
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.net.InetAddress

class HackerNewsStoryRepositoryTest {

    lateinit var hackerNewsStoryRepository: HackerNewsStoryRepository

    lateinit var mockWebServer: MockWebServer
    @Before
    fun initMockServer() {
        mockWebServer = MockWebServer()
        mockWebServer.setDispatcher(object : Dispatcher() {
            override fun dispatch(request: RecordedRequest?): MockResponse {
                return when (request?.path) {
                    "/v0/user/gajju3588.json" -> MockResponse().setResponseCode(200)
                        .setBody(getJsonFromFile("mock-responses/19796071.json"))
                    else -> MockResponse().setResponseCode(404)
                }
            }
        })
        mockWebServer.start(InetAddress.getByName("192.168.31.108"), 8081)

        val okHttpClient = OkHttpClient.Builder()
            .build()

        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("http://192.168.31.108:8081")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        val hackerNewsApiService: HackerNewsApiService = retrofit.create()
        val rxSchedulers =
            RxSchedulers(AndroidSchedulers.mainThread(), Schedulers.computation(), AndroidSchedulers.mainThread())
        hackerNewsStoryRepository = HackerNewsStoryRepository(hackerNewsApiService, rxSchedulers)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testFetchUserProfile() {
        val actualResult = getJsonFromFile("mock-responses/topstories.json")
        hackerNewsStoryRepository.fetchStoryIds(Story.TOP) {
            Assert.assertTrue(it is Success)
            Assert.assertEquals(actualResult, (it as Success).value)
        }
    }
}