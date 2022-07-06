package com.princeoprince.notekeeper

import androidx.lifecycle.ViewModel

class ItemsActivityViewModel: ViewModel() {
    var navDrawerDisplaySelection = R.id.nav_notes

    val recentlyViewedNotes = ArrayList<NoteInfo>(MAX_RECENTLY_VIEW_NOTES)

    fun addToRecentlyViewedNotes(note: NoteInfo) {
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