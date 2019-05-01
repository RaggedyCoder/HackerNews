package com.hacker.news.viewmodel

import android.content.Intent
import com.hacker.news.ui.activity.home.HackerNewsHomeActivity
import com.hacker.news.util.SwitchActivityActionData
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class HackerNewsLauncherViewModelTest {

    lateinit var hackerNewsLauncherViewModel: HackerNewsLauncherViewModel

    @Before
    fun init() {
        hackerNewsLauncherViewModel = HackerNewsLauncherViewModel(RuntimeEnvironment.application)
    }

    @Test
    fun testPerformLaunchAction() {
        val expectedIntent = Intent(RuntimeEnvironment.application, HackerNewsHomeActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        hackerNewsLauncherViewModel.activityActionLiveData.observeForever {
            Assert.assertTrue(it is SwitchActivityActionData)
            Assert.assertEquals(expectedIntent.component, it.intent?.component)
            Assert.assertEquals(true, (it as SwitchActivityActionData).shouldFinishCallingActivity)

        }
        hackerNewsLauncherViewModel.performLaunchAction()
    }
}