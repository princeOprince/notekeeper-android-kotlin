package com.princeoprince.notekeeper

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.princeoprince.notekeeper.databinding.ActivityItemsBinding

class ItemsActivity :
    AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    NoteRecyclerAdapter.OnNoteSelectedListener {

    private lateinit var binding: ActivityItemsBinding
    private lateinit var listItems: RecyclerView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    val recentlyViewedNotes = ArrayList<NoteInfo>(MAX_RECENTLY_VIEW_NOTES)

    private val noteLayoutManager by lazy { LinearLayoutManager(this) }

    private val courseLayoutManager by lazy {
        GridLayoutManager(this, resources.getInteger(R.integer.course_grid_span))
    }

    private val noteRecyclerAdapter by lazy {
        val adapter = NoteRecyclerAdapter(this, DataManager.notes)
        adapter.setOnSelectedListener(this)
        adapter
    }

    private val courseRecyclerAdapter by lazy {
        CourseRecyclerAdapter(this, DataManager.courses.values.toList())
    }

    private val recentlyViewedNoteRecyclerAdapter by lazy {
        val adapter = NoteRecyclerAdapter(this, recentlyViewedNotes)
        adapter.setOnSelectedListener(this)
        adapter
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[ItemsActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.drawerLayout
        navView = binding.navView
        val toolbar: Toolbar = binding.appBarItems.toolbar
        listItems = binding.appBarItems.include.listItems

        setSupportActionBar(toolbar)

        binding.appBarItems.fab.setOnClickListener {
            val activityIntent = Intent(this, NoteActivity::class.java)
            startActivity(activityIntent)
        }

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        displayNotes()

    }

    private fun displayNotes() {
        listItems.layoutManager = noteLayoutManager
        listItems.adapter = noteRecyclerAdapter

        navView.menu.findItem(R.id.nav_notes).isChecked = true
    }

    private fun displayCourses() {
        listItems.layoutManager = courseLayoutManager
        listItems.adapter = courseRecyclerAdapter

        navView.menu.findItem(R.id.nav_courses).isChecked = true
    }

    private fun displayRecentlyViewedNotes() {
        listItems.layoutManager = noteLayoutManager
        listItems.adapter = recentlyViewedNoteRecyclerAdapter

        navView.menu.findItem(R.id.nav_recent_notes).isChecked = true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.items, menu)
        return true
    }

    override fun onResume() {
        super.onResume()
        listItems.adapter?.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        else super.onBackPressed()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_notes -> {
                displayNotes()
            }
            R.id.nav_courses -> {
                displayCourses()
            }
            R.id.nav_recent_notes -> {
                displayRecentlyViewedNotes()
            }
            R.id.nav_share -> {
                handleSelection(R.string.nav_share_message)
            }
            R.id.nav_send -> {
                handleSelection(R.string.nav_send_message)
            }
            R.id.nav_count -> {
                val message = getString(
                    R.string.nav_count_message_format,
                    DataManager.notes.size,
                    DataManager.courses.size
                )
                Snackbar.make(listItems, message, Snackbar.LENGTH_LONG).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun handleSelection(message: Int) {
        Snackbar.make(listItems, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onNoteSelected(note: NoteInfo) {
        addToRecentlyViewedNotes(note)
    }

    private fun addToRecentlyViewedNotes(note: NoteInfo) {
        // Check if selection is already in the list
        val existingIndex = recentlyViewedNotes.indexOf(note)
        if (existingIndex == -1) {
            // It isn't in the list...
            // Add new one to the beginning of list and remove any beyond max
            recentlyViewedNotes.add(0, note)
            for (index in recentlyViewedNotes.lastIndex downTo MAX_RECENTLY_VIEW_NOTES)
                recentlyViewedNotes.removeAt(index)
        } else {
            // It is in the list...
            // Shift the ones above down the list and make it first member of the list
            for (index in (existingIndex - 1) downTo 0)
                recentlyViewedNotes[index + 1] = recentlyViewedNotes[index]
            recentlyViewedNotes[0] = note
        }
    }
}
