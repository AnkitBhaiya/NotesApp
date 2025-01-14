package org.ankitbharadwaj.notesapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDAO {

    @Upsert
    suspend fun upsertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

   @Query("SELECT * FROM note ORDER BY title ASC")
    fun getOrderedByTitle(): Flow<List<Note>>

    @Query("SELECT * FROM note ORDER BY date DESC")
    fun getOrderedByDate(): Flow<List<Note>>

}