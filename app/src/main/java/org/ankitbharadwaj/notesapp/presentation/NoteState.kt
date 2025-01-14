package org.ankitbharadwaj.notesapp.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import org.ankitbharadwaj.notesapp.data.Note

data class NoteState (
    val notes: List<Note> = emptyList(),
    val title: MutableState<String> = mutableStateOf(""),
    val content: MutableState<String> = mutableStateOf(""),
    val isAddingNote: Boolean = false
)