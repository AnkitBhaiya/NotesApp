package org.ankitbharadwaj.notesapp.presentation

import org.ankitbharadwaj.notesapp.data.Note

sealed interface NotesEvent {

    object SortNotes:NotesEvent
    data class  DeleteNotes(var note: Note): NotesEvent
    data class SaveNote(var title: String, var content: String): NotesEvent
}