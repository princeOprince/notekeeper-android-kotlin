package com.princeoprince.notekeeper.model

import android.graphics.Color
import androidx.core.app.Person

data class CourseInfo (val courseId: String, val title: String) {
    override fun toString(): String {
        return title
    }
}

data class NoteInfo (
    var course: CourseInfo? = null,
    var title: String? = null,
    var text: String? = null,
    var comments: ArrayList<NoteComment> = ArrayList(),
    var color: Int = Color.TRANSPARENT
)

data class NoteComment(
    var sender: Person?,
    var comment: String,
    var timeStamp: Long
)