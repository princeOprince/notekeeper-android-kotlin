package com.princeoprince.notekeeper

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.princeoprince.notekeeper.databinding.ActivityNoteListBinding

class NoteListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNoteListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener {
            val activityIntent = Intent(this, MainActivity::class.java)
            startActivity(activityIntent)
        }

        binding.include.listNotes.adapter = ArrayAdapter(this,
                                                android.R.layout.simple_list_item_1,
                                                    DataManager.notes)

        binding.include.listNotes.setOnItemClickListener{ _, _, position, _ ->
            val activityIntent = Intent(this, MainActivity::class.java)
            activityIntent.putExtra(NOTE_POSITION, position)
            startActivity(activityIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        (binding.include.listNotes.adapter as ArrayAdapter<*>).notifyDataSetChanged()
    }
}