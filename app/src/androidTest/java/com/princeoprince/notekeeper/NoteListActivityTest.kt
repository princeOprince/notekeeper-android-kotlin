package com.princeoprince.notekeeper


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class NoteListActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(NoteListActivity::class.java)

    @Test
    fun noteListActivityTest() {
        val floatingActionButton =
            onView(allOf(withId(R.id.fab), childAtPosition(
                childAtPosition(withId(android.R.id.content), 0), 2),
                    isDisplayed()))

        floatingActionButton.perform(click())

        val appCompatSpinner =
            onView(allOf(withId(R.id.spinnerCourses),
                childAtPosition(allOf(withId(R.id.content),
                    childAtPosition(withClassName(
                            `is`("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                        1)), 1), isDisplayed()))

        appCompatSpinner.perform(click())

        val appCompatCheckedTextView =
            onData(allOf(instanceOf(CourseInfo::class.java)))
                .atPosition(2)
        
        appCompatCheckedTextView.perform(click())

        pressBack()

        pressBack()
        }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

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
