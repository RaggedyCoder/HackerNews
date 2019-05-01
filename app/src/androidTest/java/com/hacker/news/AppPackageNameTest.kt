package com.hacker.news

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AppPackageNameTest {

    @Test
    fun testAppPackerName() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.hacker.news", appContext.packageName)
    }
}
