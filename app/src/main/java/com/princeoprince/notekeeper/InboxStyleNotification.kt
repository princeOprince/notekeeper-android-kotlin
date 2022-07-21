package com.princeoprince.notekeeper

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat

object InboxStyleNotification {

    private const val NOTIFICATION_TAG = "Inbox Style"
    private const val NOTIFICATION_ID = 4

    fun notify(context: Context, notePosition: Int) {

        val intent = Intent(context, NoteActivity::class.java)
        intent.putExtra(NOTE_POSITION, notePosition)

        val pendingIntent = TaskStackBuilder.create(context)
            .addNextIntentWithParentStack(intent)
            .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context, INBOX_STYLE_CHANNEL)
            .setDefaults(Notification.DEFAULT_ALL)
            .setSmallIcon(R.drawable.ic_stat_reminder)
            .setContentTitle("5 New Messages")
            .setContentText("Review your messages")
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.logo))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setTicker(BIG_TEXT_NAME)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.InboxStyle()
                .addLine("Your taxes are due")
                .addLine("Free cake this tuesday")
                .addLine("Your order has shipped")
                .addLine("Your pluralsight subscription")
                .addLine("Howdy!")
            )

        notify(context, builder.build())
    }

    private fun notify(context: Context, notification: Notification) {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.notify(NOTIFICATION_TAG, NOTIFICATION_ID, notification)
    }
}