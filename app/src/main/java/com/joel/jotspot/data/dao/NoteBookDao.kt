package com.joel.jotspot.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.joel.jotspot.data.model.NoteBookEntity
import com.joel.jotspot.data.relations.NoteBookWithNotes
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteBookDao {

    @Query("SELECT * FROM NOTEBOOK_TABLE")
    fun getAllNotes() : Flow<List<NoteBookEntity>>

    @Query("SELECT * FROM NoteBook_Table WHERE noteBookId=:noteBookId")
    fun getNoteBookById(noteBookId: Int) : Flow<NoteBookEntity>

    @Query("SELECT * FROM NOTEBOOK_TABLE WHERE noteBookId=:noteBookId")
    fun getNoteBookNotesById(noteBookId : Int) : Flow<NoteBookWithNotes>

    @Insert
    suspend fun insertNoteBook(noteBookEntity: NoteBookEntity)

    @Update
    suspend fun updateNoteBook(noteBookEntity: NoteBookEntity)

    @Delete
    suspend fun deleteNoteBook(noteBookEntity: NoteBookEntity)

    @Query("DELETE FROM NOTEBOOK_TABLE")
    suspend fun deleteAllNoteBooks()


}