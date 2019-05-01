package com.hacker.news.util

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class StoryTest {

    @Test
    fun testTopStoryPathParam() {
        Assert.assertEquals("topstories", Story.TOP.storyPathParam)
    }

    @Test
    fun testNewStoryPathParam() {
        Assert.assertEquals("newstories", Story.NEW.storyPathParam)
    }

    @Test
    fun testBestStoryPathParam() {
        Assert.assertEquals("beststories", Story.BEST.storyPathParam)
    }

    @Test
    fun testAskStoryPathParam() {
        Assert.assertEquals("askstories", Story.ASK.storyPathParam)
    }

    @Test
    fun testShowStoryPathParam() {
        Assert.assertEquals("showstories", Story.SHOW.storyPathParam)
    }

    @Test
    fun testJobStoryPathParam() {
        Assert.assertEquals("jobstories", Story.JOB.storyPathParam)
    }
}