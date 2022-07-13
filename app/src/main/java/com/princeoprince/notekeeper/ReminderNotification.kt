package com.princeoprince.notekeeper

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

object ReminderNotification {

    private const val NOTIFICATION_TAG = "Reminder"

    const val REMINDER_CHANNEL = "reminders"

    fun notify(context: Context, titleText: String, noteText: String, number: Int) {

        val shareIntent = PendingIntent.getActivity(
            context,
            0,
            Intent.createChooser(Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT, noteText),
        "Share Note Reminder"),
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, REMINDER_CHANNEL)
            .setDefaults(Notification.DEFAULT_ALL)
            .setSmallIcon(R.drawable.ic_stat_reminder)
            .setContentTitle(titleText)
            .setContentText(noteText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setTicker(titleText)
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    0,
                    Intent(context, ItemsActivity::class.java),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
            .setAutoCancel(true)
            .addAction(android.R.drawable.ic_menu_share, "Share", shareIntent)

        notify(context, builder.build())
    }

    private fun notify(context: Context, notification: Notification) {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.notify(NOTIFICATION_TAG, 0, notification)
    }

    fun cancel(context: Context) {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.cancel(NOTIFICATION_TAG, 0)
    }
}