package com.princeoprince.notekeeper

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */
class NoteKeeperAppWidget : AppWidgetProvider() {

    companion object {
        fun sendRefreshBroadcast(context: Context) {
            val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE).apply {
                component = ComponentName(context, NoteKeeperAppWidget::class.java)
            }
            context.sendBroadcast(intent)
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
            // Refresh all widgets
            val manager = AppWidgetManager.getInstance(context)
            val componentName = ComponentName(context, NoteKeeperAppWidget::class.java)
            manager.notifyAppWidgetViewDataChanged(
                manager.getAppWidgetIds(componentName), R.id.notes_list)
        }
        super.onReceive(context, intent)
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val widgetText = context.getString(R.string.notes)

    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.note_keeper_app_widget)
    views.setTextViewText(R.id.appwidget_text, widgetText)
    views.setRemoteAdapter(
        R.id.notes_list,
        Intent(context, AppWidgetRemoteViewsService::class.java)
    )

    val intent = Intent(context, NoteActivity::class.java)

    val pendingIntent = TaskStackBuilder.create(context)
        .addNextIntentWithParentStack(intent)
        .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

    views.setPendingIntentTemplate(R.id.notes_list, pendingIntent)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}