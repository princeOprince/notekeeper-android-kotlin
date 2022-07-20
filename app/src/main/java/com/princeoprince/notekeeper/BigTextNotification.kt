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

object BigTextNotification {

    private const val NOTIFICATION_TAG = "Big Text Style"
    private const val NOTIFICATION_ID = 2

    fun notify(context: Context, notePosition: Int) {

        val intent = Intent(context, NoteActivity::class.java)
        intent.putExtra(NOTE_POSITION, notePosition)

        val pendingIntent = TaskStackBuilder.create(context)
            .addNextIntentWithParentStack(intent)
            .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context, BIG_TEXT_CHANNEL)
            .setDefaults(Notification.DEFAULT_ALL)
            .setSmallIcon(R.drawable.ic_stat_reminder)
            .setContentTitle("Collapsed Title")
            .setContentText("Collapsed Body Text")
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.example))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setTicker(BIG_TEXT_NAME)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(context.resources.getString(R.string.big_text))
                .setBigContentTitle("Big Content Title")
                .setSummaryText("Summary Text")
            )

        notify(context, builder.build())
    }

    private fun notify(context: Context, notification: Notification) {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.notify(NOTIFICATION_TAG, NOTIFICATION_ID, notification)
    }
}