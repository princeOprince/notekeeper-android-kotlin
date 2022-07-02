package com.princeoprince.notekeeper

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matchers.*
import androidx.test.espresso.action.ViewActions.*
import org.junit.Rule
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.NavigationViewActions

@RunWith(AndroidJUnit4::class)
class NavigationTest {
    @Rule @JvmField
    val itemsActivity = ActivityScenarioRule(ItemsActivity::class.java)

    @Test
    fun selectNoteAfterNavigationDrawerChange() {

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_courses))

        val coursePosition = 0
        onView(withId(R.id.listItems)).perform(
            RecyclerViewActions
                .actionOnItemAtPosition<CourseRecyclerAdapter.ViewHolder>(
                    coursePosition, click())
        )

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_notes))

        val notePosition = 0
        onView(withId(R.id.listItems)).perform(
            RecyclerViewActions
                .actionOnItemAtPosition<NoteRecyclerAdapter.ViewHolder>(
                    notePosition, click())
        )
    }

}