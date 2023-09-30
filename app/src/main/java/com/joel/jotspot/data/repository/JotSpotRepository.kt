package com.joel.jotspot.data.repository

import com.joel.jotspot.data.model.NoteEntity
import com.joel.jotspot.data.model.TagEntity
import kotlinx.coroutines.flow.Flow

interface JotSpotRepository {

    val allNotes : Flow<List<NoteEntity>>
    //Notes Implementations

    fun getNoteById(id : Int) : Flow<NoteEntity>

    suspend fun insertNote(noteEntity: NoteEntity)

    suspend fun updateNote(noteEntity: NoteEntity)

    suspend fun deleteNote(noteEntity: NoteEntity)

    suspend fun deleteAllNotes()


    //Tags implementations
    suspend fun searchByQuery(query : String) : Flow<List<NoteEntity>>

    fun getAllTags() : Flow<List<TagEntity>>

    fun insertTag(tagEntity: TagEntity)

    fun deleteAllTags()
}