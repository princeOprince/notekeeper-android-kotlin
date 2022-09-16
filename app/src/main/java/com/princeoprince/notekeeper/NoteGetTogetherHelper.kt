package com.princeoprince.notekeeper

import android.content.Context
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.princeoprince.notekeeper.model.NoteInfo

class NoteGetTogetherHelper(
    private val context: Context,
    private val lifecycle: Lifecycle): LifecycleObserver {

    init {
        lifecycle.addObserver(this)
    }

    var location = "on"

    private val tag = this::class.simpleName
    private var currentLat = 0.0
    private var currentLon = 0.0

    private val locManager = PseudoLocationManager(context) { lat, lon ->
        currentLat = lat
        currentLon = lon
        Log.d(tag, "Location Callback Lat:$currentLat Lon:$currentLon")
    }

    private val msgManager = PseudoMessagingManager(context)
    private var msgConnection: PseudoMessagingConnection? = null

    fun sendMessage(note: NoteInfo) {
        val getTogetherMessage =
            "$currentLat | $currentLon | ${note.title} | ${note.course?.title}"
        msgConnection?.send(getTogetherMessage)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startHandler() {
        Log.d(tag, "startHandler")
        locManager.start()
        msgManager.connect { connection ->
            Log.d(tag, "Connection callback - Lifecycle state:${lifecycle.currentState}")
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED))
                msgConnection = connection
            else
                connection.disconnect()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopHandler() {
        Log.d(tag, "stopHandler")
        locManager.stop()
        msgConnection?.disconnect()
    }

    fun turnOffLocationCallback() {
        locManager.stop()
        location = "off"
    }

    fun turnOnLocationCallback() {
        locManager.start()
        location = "on"
    }
}