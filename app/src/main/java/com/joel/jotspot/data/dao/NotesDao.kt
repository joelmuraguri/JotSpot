package com.joel.jotspot.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.joel.jotspot.data.model.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM NOTES_TABLE")
    fun getAllNotes() : Flow<List<NoteEntity>>

    @Query("SELECT * FROM Notes_table WHERE id=:id")
    fun getNoteById(id : Int) : Flow<NoteEntity>

    @Insert
    suspend fun insertNote(notes: NoteEntity)

    @Update
    suspend fun updateNotes(notes: NoteEntity)

    @Delete
    suspend fun deleteNote(notes: NoteEntity)

    // To be Confirmed
    @Query("DELETE FROM NOTES_TABLE")
    suspend fun deleteAllNotes()

    //Make query in the db based on the query or tags
//    suspend fun searchForNotes(query : String) : Flow<List<NoteEntity>>
//
//    suspend fun searchByTags() : Flow<List<NoteEntity>>

}