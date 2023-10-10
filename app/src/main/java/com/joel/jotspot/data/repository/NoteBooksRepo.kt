package com.joel.jotspot.data.repository

import com.joel.jotspot.data.model.NoteBookEntity
import com.joel.jotspot.data.relations.NoteBookWithNotes
import kotlinx.coroutines.flow.Flow

interface NoteBooksRepo {

    val allNoteBooks : Flow<List<NoteBookEntity>>

    fun getNoteBookById(noteBookId : Int) : Flow<NoteBookEntity>

    fun getNotesByNoteBookId(noteBookId : Int) : Flow<NoteBookWithNotes>

    suspend fun insertNoteBook(noteBookEntity: NoteBookEntity)

    suspend fun updateNoteBook(noteBookEntity: NoteBookEntity)

    suspend fun deleteNoteBook(noteBookEntity: NoteBookEntity)

    suspend fun deleteAllNoteBooks()
}