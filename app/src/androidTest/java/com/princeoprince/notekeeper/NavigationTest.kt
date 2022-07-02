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

    }
}