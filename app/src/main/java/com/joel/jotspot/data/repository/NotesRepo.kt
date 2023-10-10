package com.joel.jotspot.data.repository

import com.joel.jotspot.data.model.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NotesRepo {


    val allNotes : Flow<List<NoteEntity>>

    fun getNoteById(id : Int) : Flow<NoteEntity>

    suspend fun insertNote(noteEntity: NoteEntity)

    suspend fun updateNote(noteEntity: NoteEntity)

    suspend fun deleteNote(noteEntity: NoteEntity)

    suspend fun deleteAllNotes()

}