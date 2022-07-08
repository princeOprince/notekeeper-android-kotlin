package com.princeoprince.notekeeper

import android.content.Context
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class NoteGetTogetherHelper(context: Context, lifecycle: Lifecycle): LifecycleObserver {

    init {
        lifecycle.addObserver(this)
    }

    private val tag = this::class.simpleName
    private var currentLat = 0.0
    private var currentLon = 0.0

    private val locManager = PseudoLocationManager(context) { lat, lon ->
        currentLat = lat
        currentLon = lon
        Log.d(tag, "Location Callback Lat:$currentLat Lon:$currentLon")
    }

    val msgManager = PseudoMessagingManager(context)
    var msgConnection: PseudoMessagingConnection? = null

    fun sendMessage(note: NoteInfo) {
        val getTogetherMessage =
            "$currentLat | $currentLon | ${note.title} | ${note.course?.title}"
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startHandler() {
        Log.d(tag, "startHandler")
        locManager.start()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopHandler() {
        Log.d(tag, "stopHandler")
        locManager.stop()
    }
}