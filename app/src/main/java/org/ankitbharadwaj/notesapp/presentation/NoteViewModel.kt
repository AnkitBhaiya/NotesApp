package org.ankitbharadwaj.notesapp.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Dao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ankitbharadwaj.notesapp.data.Note
import org.ankitbharadwaj.notesapp.data.NotesDAO

class NoteViewModel(
    private var dao: NotesDAO
) : ViewModel() {

    private var isSortedByDateAdded = MutableStateFlow(true)
    private var notes = isSortedByDateAdded.flatMapLatest {
        if (it) {
            dao.getOrderedByDate()
        } else {
            dao.getOrderedByTitle()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    var _state = MutableStateFlow(NoteState())
    var state = combine(_state, isSortedByDateAdded, notes) { state, isSortedByDateAdded, notes ->{
        state.copy(
            notes = notes)
    }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteState())

    fun onEvent(event: NotesEvent){
        when(event){
            is NotesEvent.DeleteNotes ->{
                viewModelScope.launch {
                    dao.deleteNote(event.note)
                }
            }
            is NotesEvent.SaveNote -> {
               val note = Note(
                   title = event.title,
                   content = event.content,
                   date = System.currentTimeMillis()
               )
                viewModelScope.launch {
                    dao.upsertNote(note)
                }
                _state.update {
                    it.copy(
                        isAddingNote = false,
                        title = mutableStateOf(""),
                        content = mutableStateOf("")
                    )
                }
            }
            NotesEvent.SortNotes -> {
                isSortedByDateAdded.value = !isSortedByDateAdded.value

            }
        }

    }

}