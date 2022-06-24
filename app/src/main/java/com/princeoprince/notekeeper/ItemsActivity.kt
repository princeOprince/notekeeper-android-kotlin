package com.princeoprince.notekeeper

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.princeoprince.notekeeper.databinding.ActivityItemsBinding

class ItemsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityItemsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val toolbar: Toolbar = binding.appBarItems.toolbar
        val listItems: RecyclerView = binding.appBarItems.include.listItems

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

        listItems.layoutManager = LinearLayoutManager(this)
        listItems.adapter = NoteRecyclerAdapter(this, DataManager.notes)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.items, menu)
        return true
    }

    override fun onResume() {
        super.onResume()
        binding.appBarItems.include.listItems.adapter?.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        else super.onBackPressed()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_home -> {}
            R.id.nav_gallery -> {}
            R.id.nav_slideshow -> {}
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}

