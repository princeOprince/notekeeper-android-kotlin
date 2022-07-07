package com.princeoprince.notekeeper

object DataManager {
    val courses = HashMap<String, CourseInfo>()
    val notes = ArrayList<NoteInfo>()

    init {
        initialiseCourses()
        initialiseNotes()
    }

    fun loadNotes(vararg noteIds: Int): List<NoteInfo> {
        val noteList: List<NoteInfo>

        if (noteIds.isEmpty())
            noteList = notes
        else {
            noteList = ArrayList<NoteInfo>(noteIds.size)
            for (noteId in noteIds)
                noteList.add(notes[noteId])
        }
        return noteList
    }

    fun loadNote(noteId: Int) = notes[noteId]

    fun isLastNoteId(noteId: Int) = noteId == notes.lastIndex

    private fun idOfNote(note: NoteInfo) = notes.indexOf(note)

    fun noteIdsAsIntArray(notes: List<NoteInfo>): IntArray {
        val noteIds = IntArray(notes.size)
        for (index in 0..notes.lastIndex)
            noteIds[index] = idOfNote(notes[index])
        return noteIds
    }

    fun addNote(course: CourseInfo, noteTitle: String, noteText: String): Int {
        return addNote(NoteInfo(course, noteTitle, noteText))
    }

    private fun addNote(note: NoteInfo): Int {
        notes.add(note)
        return notes.lastIndex
    }

    fun findNote(course: CourseInfo, noteTitle: String, noteText: String): NoteInfo? {
        for (note in notes) {
            if (course == note.course && noteTitle == note.title && noteText == note.text)
                return note
        }
        return null
    }

    private fun initialiseCourses() {
        var course = CourseInfo("android_intents", "Android Programming with Intents")
        courses[course.courseId] = course

        course = CourseInfo(courseId = "android_async", title = "Android Async Programming and Services")
        courses[course.courseId] = course

        course = CourseInfo(title = "Java Fundamentals: The Java Language", courseId = "java_lang")
        courses[course.courseId] = course

        course = CourseInfo("java_core", "Java Fundamentals: The Core Platform")
        courses[course.courseId] = course
    }

    fun initialiseNotes() {
        var course = courses["android_intents"]
        notes.add(NoteInfo(course!!, "Dynamic intent resolution",
            "Wow, intents allow components to be resolved at runtime"))
        notes.add(NoteInfo(course, "Delegating intents",
            "PendingIntents are powerful; they delegate much more than just a component invocation"))

        course = courses["android_async"]
        notes.add(NoteInfo(course!!, "Service default threads",
            "Did you know that by default an Android Service will tie up the UI thread?"))
        notes.add(NoteInfo(course, "Long running operations",
            "Foreground Services can be tied to a notification icon"))

        course = courses["java_lang"]
        notes.add(NoteInfo(course!!, "Parameters",
            "Leverage variable-length parameter lists"))
        notes.add(NoteInfo(course, "Anonymous classes",
            "Anonymous classes simplify implementing one-use types"))

        course = courses["java_core"]
        notes.add(NoteInfo(course!!, "Compiler options",
            "The -jar option isn't compatible with with the -cp option"))
        notes.add(NoteInfo(course, "Serialization",
            "Remember to include SerialVersionUID to assure version compatibility"))
    }
}