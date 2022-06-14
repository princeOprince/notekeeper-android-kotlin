package com.princeoprince.notekeeper

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matchers.*
import androidx.test.espresso.action.ViewActions.*
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class CreateNewNoteTest{

    @Rule @JvmField
    val noteListActivity = ActivityScenarioRule(NoteListActivity::class.java)

    @Test
    fun createNewNote() {

    }
}