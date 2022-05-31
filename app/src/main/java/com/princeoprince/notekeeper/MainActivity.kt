package com.princeoprince.notekeeper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.princeoprince.notekeeper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var notePosition = POSITION_NOT_SET

    private lateinit var mainBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        setSupportActionBar(mainBinding.toolbar)

        val adapterCourses = ArrayAdapter<CourseInfo>(this,
                android.R.layout.simple_spinner_item,
                DataManager.courses.values.toList())
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        mainBinding.content.spinnerCourses.adapter = adapterCourses

        notePosition = savedInstanceState?.getInt(NOTE_POSITION, POSITION_NOT_SET) ?:
            intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET)

        if (notePosition != POSITION_NOT_SET)
            displayNote()
        else {
            createNewNote()
        }
    }

    private fun createNewNote() {
        DataManager.notes.add(NoteInfo())
        notePosition = DataManager.notes.lastIndex
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(NOTE_POSITION, notePosition)
    }

    private fun displayNote() {
        val note = DataManager.notes[notePosition]
        mainBinding.content.textNoteTitle.setText(note.title)
        mainBinding.content.textNoteText.setText(note.text)

        val coursePosition = DataManager.courses.values.indexOf(note.course)
        mainBinding.content.spinnerCourses.setSelection(coursePosition)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_next -> {
                moveNext()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun moveNext() {
        ++notePosition
        displayNote()
        invalidateOptionsMenu()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (notePosition >= DataManager.notes.lastIndex) {
            val menuItem = menu?.findItem(R.id.action_next)
            if (menuItem != null) {
                menuItem.icon = getDrawable(R.drawable.ic_baseline_block_24)
                menuItem.isEnabled = false
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onPause() {
        super.onPause()
        saveNote()
    }

    private fun saveNote() {
        val note = DataManager.notes[notePosition]
        note.title = mainBinding.content.textNoteTitle.text.toString()
        note.text = mainBinding.content.textNoteText.text.toString()
        note.course = mainBinding.content.spinnerCourses.selectedItem as CourseInfo
    }
}