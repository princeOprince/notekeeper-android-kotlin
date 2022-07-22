package com.princeoprince.notekeeper

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import androidx.core.app.RemoteInput
import androidx.core.content.ContextCompat

object MessagingStyleNotification {

    private const val NOTIFICATION_TAG = "Messaging Style"
    private const val NOTIFICATION_ID = 5

    fun notify(context: Context, note: NoteInfo, notePosition: Int) {

        val intent = Intent(context, NoteActivity::class.java)
        intent.putExtra(NOTE_POSITION, notePosition)

        val pendingIntent = TaskStackBuilder.create(context)
            .addNextIntentWithParentStack(intent)
            .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        val sender = Person.Builder().setName("You").build()

        val message1 = NotificationCompat.MessagingStyle.Message(
            note.comments[0].comment,
            note.comments[0].timeStamp,
            note.comments[0].sender
        )

        val message2 = NotificationCompat.MessagingStyle.Message(
            note.comments[1].comment,
            note.comments[1].timeStamp,
            note.comments[1].sender
        )

        val message3 = NotificationCompat.MessagingStyle.Message(
            note.comments[2].comment,
            note.comments[2].timeStamp,
            note.comments[2].sender
        )

        val remoteInput = RemoteInput.Builder(KEY_TEXT_REPLY)
            .setLabel("Add Note")
            .build()

        val replyIntent = Intent(context, NotificationBroadcastReceiver::class.java).also {
            it.putExtra(NOTE_POSITION, notePosition)
        }

        val replyPendingIntent = PendingIntent.getBroadcast(
            context, 100, replyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val replyAction = NotificationCompat.Action.Builder(
            android.R.drawable.ic_menu_send, "Add Note", replyPendingIntent
        ).addRemoteInput(remoteInput).build()

        val builder = NotificationCompat.Builder(context, MESSAGING_STYLE_CHANNEL)
            .setDefaults(Notification.DEFAULT_ALL)
            .setSmallIcon(R.drawable.ic_stat_reminder)
            .setContentTitle("Comments about ${note.title}")
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.logo))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setTicker("Comments about ${note.title}")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setColor(ContextCompat.getColor(context, R.color.darkOrange))
            .setColorized(true)
            .setOnlyAlertOnce(true)
            .setStyle(NotificationCompat.MessagingStyle(sender)
                .setConversationTitle(note.title)
                .addMessage(message3)
                .addMessage(message2)
                .addMessage(message1))
            .addAction(replyAction)

        notify(context, builder.build())
    }

    private fun notify(context: Context, notification: Notification) {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.notify(NOTIFICATION_TAG, NOTIFICATION_ID, notification)
    }
}