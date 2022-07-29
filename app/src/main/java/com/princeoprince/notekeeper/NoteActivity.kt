package com.princeoprince.notekeeper

import android.app.Notification
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import com.princeoprince.notekeeper.databinding.ActivityMainBinding

class NoteActivity : AppCompatActivity() {
    private val tag = this::class.simpleName
    private var notePosition = POSITION_NOT_SET
    private var isNewNote = false
    private var isCancelling = false
    private var noteColor: Int = Color.TRANSPARENT

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var spinnerCourses: Spinner
    private lateinit var textNoteTitle: EditText
    private lateinit var textNoteText: EditText
    private lateinit var colorSelector: ColorSelector

    val noteGetTogetherHelper = NoteGetTogetherHelper(this, lifecycle)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        setSupportActionBar(mainBinding.toolbar)

        spinnerCourses = mainBinding.content.spinnerCourses
        textNoteTitle = mainBinding.content.textNoteTitle
        textNoteText = mainBinding.content.textNoteText
        colorSelector = mainBinding.content.colorSelector!!

        val adapterCourses = ArrayAdapter(this,
                android.R.layout.simple_spinner_item,
                DataManager.courses.values.toList())
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerCourses.adapter = adapterCourses

        notePosition = savedInstanceState?.getInt(NOTE_POSITION, POSITION_NOT_SET) ?:
            intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET)

        if (notePosition != POSITION_NOT_SET)
            displayNote()
        else {
            createNewNote()
        }
        Log.d(tag, "onCreate")

        val commentsAdapter = CommentRecyclerAdapter(this, DataManager.notes[notePosition])
        mainBinding.content.commentsList?.layoutManager = LinearLayoutManager(this)
        mainBinding.content.commentsList?.adapter = commentsAdapter

        colorSelector.setColorSelectListener(
            object: ColorSelector.ColorSelectListener {
                override fun onColorSelected(color: Int) {
                    noteColor = color
                }
            }
        )
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        notePosition = intent?.getIntExtra(NOTE_POSITION, POSITION_NOT_SET) ?: POSITION_NOT_SET
    }

    private fun createNewNote() {
        isNewNote = true
        DataManager.notes.add(NoteInfo())
        notePosition = DataManager.notes.lastIndex
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(NOTE_POSITION, notePosition)
    }

    private fun displayNote() {
        Log.i(tag, "Displaying note for position $notePosition")
        val note = DataManager.notes[notePosition]
        textNoteTitle.setText(note.title)
        textNoteText.setText(note.text)
        noteColor = note.color
        colorSelector.setSelectedColor(note.color)


        val coursePosition = DataManager.courses.values.indexOf(note.course)
        spinnerCourses.setSelection(coursePosition)
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
            R.id.action_get_together -> {
                noteGetTogetherHelper.sendMessage(DataManager.loadNote(notePosition))
                true
            }
            R.id.action_cancel -> {
                isCancelling = true
                finish()
                true
            }
            R.id.action_reminder -> {
                ReminderNotification.notify(
                    this,
                    "Reminder",
                    getString(R.string.reminder_body, DataManager.notes[notePosition].title),
                     notePosition
                )
                true
            }
            R.id.action_quick_view -> {
                QuickViewNotification.notify(
                    this,
                    "Quick View Reminder",
                    getString(R.string.reminder_body, DataManager.notes[notePosition].title),
                     notePosition
                )
                true
            }
            R.id.action_big_text -> {
                BigTextNotification.notify(
                    this,
                     notePosition
                )
                true
            }
            R.id.action_big_picture -> {
                BigPictureNotification.notify(
                    this,
                     notePosition
                )
                true
            }
            R.id.action_inbox_style -> {
                InboxStyleNotification.notify(
                    this,
                     notePosition
                )
                true
            }
            R.id.action_messaging_style -> {
                MessagingStyleNotification.notify(
                    this,
                    DataManager.notes[notePosition],
                     notePosition
                )
                true
            }
            R.id.action_location -> {
                if (noteGetTogetherHelper.location == "on") {
                    noteGetTogetherHelper.turnOffLocationCallback()
                    item.title = "Turn on location"
                } else {
                    noteGetTogetherHelper.turnOnLocationCallback()
                    item.title = "Turn off location"
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun moveNext() {
        saveNote()
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
        when {
            isCancelling -> {
                if (isNewNote)
                    DataManager.notes.removeAt(notePosition)
            }
            else -> saveNote()
        }
        Log.d(tag, "onPause")
    }

    private fun saveNote() {
        val note = DataManager.notes[notePosition]
        note.title = textNoteTitle.text.toString()
        note.text = textNoteText.text.toString()
        note.course = spinnerCourses.selectedItem as CourseInfo
        note.color = this.noteColor
        NoteKeeperAppWidget.sendRefreshBroadcast(this)
    }
}