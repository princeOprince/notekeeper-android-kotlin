package com.princeoprince.notekeeper

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleObserver

class NoteGetTogetherHelper(val context: Context): LifecycleObserver {

    val tag = this::class.simpleName
    var currentLat = 0.0
    var currentLon = 0.0

    val locManager = PseudoLocationManager(context) { lat, lon ->
        currentLat = lat
        currentLon = lon
        Log.d(tag, "Location Callback Lat:$currentLat Lon:$currentLon")
    }
}