package com.hacker.news.ui.activity

import android.content.Intent
import com.hacker.news.ui.activity.home.HackerNewsHomeActivity
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows.shadowOf


@RunWith(RobolectricTestRunner::class)
class HackerNewsLauncherActivityTest {

    @Test
    fun testNewsLauncherActivity() {
        val launcherActivity = Robolectric.setupActivity(HackerNewsLauncherActivity::class.java)
        val expectedIntent = Intent(RuntimeEnvironment.application, HackerNewsHomeActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val actual = shadowOf(RuntimeEnvironment.application).nextStartedActivity
        Assert.assertEquals(expectedIntent.component, actual.component)
    }
}