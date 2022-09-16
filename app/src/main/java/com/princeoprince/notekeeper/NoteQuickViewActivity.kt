package com.princeoprince.notekeeper

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.princeoprince.notekeeper.databinding.ActivityNoteQuickViewBinding
import com.princeoprince.notekeeper.model.DataManager

class NoteQuickViewActivity : AppCompatActivity() {

    private var notePosition = POSITION_NOT_SET
    private lateinit var binding: ActivityNoteQuickViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNoteQuickViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notePosition = savedInstanceState?.getInt(EXTRA_NOTE_POSITION, POSITION_NOT_SET) ?:
            intent.getIntExtra(EXTRA_NOTE_POSITION, POSITION_NOT_SET)

        initLayout()
        setNote()
    }

    private fun initLayout() {
        binding.deleteButton.setOnClickListener {
            DataManager.notes.removeAt(notePosition)
            finish()
        }
    }

    private fun setNote() {
        if (notePosition != POSITION_NOT_SET) {
            val note = DataManager.notes[notePosition]
            binding.courseTitle.text = note.course?.title
            binding.noteTitle.text = note.title
            binding.noteText.text = note.text
        } else {
            Snackbar.make(
                binding.root, getString(R.string.note_load_error), Snackbar.LENGTH_INDEFINITE
            ).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(EXTRA_NOTE_POSITION, notePosition)
    }

    companion object {
        const val EXTRA_NOTE_POSITION = "notePosition"

        fun getIntent(context: Context, notePosition: Int) : Intent {
            val intent = Intent(context, NoteQuickViewActivity::class.java)
            intent.putExtra(EXTRA_NOTE_POSITION, notePosition)
            return intent
        }
    }
}