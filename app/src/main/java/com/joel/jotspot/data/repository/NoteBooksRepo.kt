package com.joel.jotspot.data.repository

import com.joel.jotspot.data.relations.NoteBookWithNotes
import kotlinx.coroutines.flow.Flow

interface NoteBooksRepo {

    val allNoteBooks : Flow<List<com.joel.jotspot.data.model.NoteBookEntity>>

    fun getNoteBookById(noteBookId : Int) : Flow<com.joel.jotspot.data.model.NoteBookEntity>

    fun getNotesByNoteBookId(noteBookId : Int) : Flow<NoteBookWithNotes>

    suspend fun insertNoteBook(noteBookEntity: com.joel.jotspot.data.model.NoteBookEntity)

    suspend fun updateNoteBook(noteBookEntity: com.joel.jotspot.data.model.NoteBookEntity)

    suspend fun deleteNoteBook(noteBookEntity: com.joel.jotspot.data.model.NoteBookEntity)

    suspend fun deleteAllNoteBooks()
}