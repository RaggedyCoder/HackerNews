package com.hacker.news.util

import android.widget.Toast
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows

@RunWith(RobolectricTestRunner::class)
class InlineFunctionsTest {

    @Test
    fun testShowToast() {
        RuntimeEnvironment.application.showToast("Hello World")
        val toasts = Shadows.shadowOf(RuntimeEnvironment.application).shownToasts
        Assert.assertEquals(1, toasts.size)
        Assert.assertNotNull(toasts[0])
        Assert.assertEquals(Toast.LENGTH_SHORT, toasts[0].duration)
    }
}