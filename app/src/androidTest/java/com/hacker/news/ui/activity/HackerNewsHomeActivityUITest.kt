package com.hacker.news.ui.activity


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.hacker.news.R
import com.hacker.news.ui.activity.home.HackerNewsHomeActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class HackerNewsHomeActivityUITest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(HackerNewsHomeActivity::class.java)

    //lateinit var MockServ

    //[19791683,19795481

    @Test
    fun testHackerNewsLauncherToolbarText() {

        val appCompatImageButton = onView(
            allOf(
                withContentDescription("Hacker News"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withClassName(`is`("com.google.android.material.appbar.AppBarLayout")),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())
    }

    @Test
    fun testNavigationBarTitleText() {
        Thread.sleep(700)
        val appCompatImageButton = onView(
            allOf(
                withContentDescription("Hacker News"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withClassName(`is`("com.google.android.material.appbar.AppBarLayout")),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val textView = onView(
            allOf(
                withText("Hacker News"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.navigation_header_container),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView.check(ViewAssertions.matches(withText("Hacker News")))
        textView.check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun testNavigationHeaderImageView() {
        Thread.sleep(700)
        val appCompatImageButton = onView(
            allOf(
                withContentDescription("Hacker News"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withClassName(`is`("com.google.android.material.appbar.AppBarLayout")),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val imageView = onView(
            allOf(
                withId(R.id.appIconImageView),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.navigation_header_container),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        imageView.check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun testToolbar(){
        val viewGroup = onView(
            allOf(
                withId(R.id.toolbar),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.view.ViewGroup::class.java),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        viewGroup.check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun testRecyclerViewClickAction(){
        val viewGroup = onView(
            allOf(
                withId(R.id.toolbar),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.view.ViewGroup::class.java),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        viewGroup.check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun testTopStoriesMenu() {
        Thread.sleep(700)
        val appCompatImageButton = onView(
            allOf(
                withContentDescription("Hacker News"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withClassName(`is`("com.google.android.material.appbar.AppBarLayout")),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val checkedTextView = onView(
            allOf(
                withId(R.id.design_menu_item_text),
                withText("Top Stories"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.design_navigation_view),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        checkedTextView.check(ViewAssertions.matches(withText("Top Stories")))
        checkedTextView.check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun testNewStoriesMenu() {
        Thread.sleep(700)
        val appCompatImageButton = onView(
            allOf(
                withContentDescription("Hacker News"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withClassName(`is`("com.google.android.material.appbar.AppBarLayout")),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val checkedTextView = onView(
            allOf(
                withId(R.id.design_menu_item_text),
                withText("New Stories"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.design_navigation_view),
                        2
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        checkedTextView.check(ViewAssertions.matches(withText("New Stories")))
        checkedTextView.check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun testBestStoriesMenu() {
        Thread.sleep(700)
        val appCompatImageButton = onView(
            allOf(
                withContentDescription("Hacker News"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withClassName(`is`("com.google.android.material.appbar.AppBarLayout")),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val checkedTextView = onView(
            allOf(
                withId(R.id.design_menu_item_text),
                withText("Best Stories"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.design_navigation_view),
                        3
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        checkedTextView.check(ViewAssertions.matches(withText("Best Stories")))
        checkedTextView.check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun testAskStoriesMenu() {
        Thread.sleep(700)
        val appCompatImageButton = onView(
            allOf(
                withContentDescription("Hacker News"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withClassName(`is`("com.google.android.material.appbar.AppBarLayout")),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val checkedTextView = onView(
            allOf(
                withId(R.id.design_menu_item_text),
                withText("Ask Stories"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.design_navigation_view),
                        4
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        checkedTextView.check(ViewAssertions.matches(withText("Ask Stories")))
        checkedTextView.check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun testShowStoriesMenu() {
        Thread.sleep(700)
        val appCompatImageButton = onView(
            allOf(
                withContentDescription("Hacker News"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withClassName(`is`("com.google.android.material.appbar.AppBarLayout")),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val checkedTextView = onView(
            allOf(
                withId(R.id.design_menu_item_text),
                withText("Show Stories"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.design_navigation_view),
                        5
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        checkedTextView.check(ViewAssertions.matches(withText("Show Stories")))
        checkedTextView.check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun testJobStoriesMenu() {
        Thread.sleep(700)
        val appCompatImageButton = onView(
            allOf(
                withContentDescription("Hacker News"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withClassName(`is`("com.google.android.material.appbar.AppBarLayout")),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val checkedTextView = onView(
            allOf(
                withId(R.id.design_menu_item_text),
                withText("Job Stories"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.design_navigation_view),
                        6
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        checkedTextView.check(ViewAssertions.matches(withText("Job Stories")))
        checkedTextView.check(ViewAssertions.matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
