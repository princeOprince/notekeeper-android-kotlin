package com.princeoprince.notekeeper

import android.app.RemoteInput
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.princeoprince.notekeeper.model.DataManager
import com.princeoprince.notekeeper.model.NoteComment

class NotificationBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val bundle = RemoteInput.getResultsFromIntent(intent)
        if (bundle != null) {
            val notePosition = intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET)
            val text = bundle.getCharSequence(KEY_TEXT_REPLY)?.toString() ?: ""
            DataManager.notes[notePosition].comments.add(
                0, NoteComment(null, text, System.currentTimeMillis())
            )
            MessagingStyleNotification.notify(
                context, DataManager.notes[notePosition], notePosition
            )
            bundle.clear()
        }
    }
}