package com.hacker.news.ui.activity


import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.hacker.news.R
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class HackerNewsLauncherActivityTest2 {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(HackerNewsLauncherActivity::class.java)

    lateinit var mockWebServer: MockWebServer
    @Before
    fun initMockServer() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        val mockedResponse = MockResponse()
        mockedResponse.setResponseCode(200)
        mockedResponse.setBody("[19791683,19795481]")
        mockWebServer.enqueue(mockedResponse)
    }

    @After
    @Throws
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun hackerNewsLauncherActivityTest2() {
        Thread.sleep(5000)
        Espresso.onView(
            allOf(
                withId(R.id.hackerNewsItemRecyclerView),
                withClassName(`is`("androidx.recyclerview.widget.RecyclerView"))
            )
        ).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
    }
}
