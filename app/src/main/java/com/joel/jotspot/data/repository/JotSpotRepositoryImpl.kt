package com.joel.jotspot.data.repository

import com.joel.jotspot.data.dao.NotesDao
import com.joel.jotspot.data.dao.TagsDao
import com.joel.jotspot.data.model.NoteEntity
import com.joel.jotspot.data.model.TagEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class JotSpotRepositoryImpl @Inject constructor(
    private val notesDao: NotesDao,
    private val tagsDao: TagsDao,
) : JotSpotRepository {

    override val allNotes: Flow<List<NoteEntity>> = notesDao.getAllNotes()

    override fun getNoteById(id: Int): Flow<NoteEntity> {
        return notesDao.getNoteById(id)
    }

    override suspend fun insertNote(noteEntity: NoteEntity) {
        return notesDao.insertNote(noteEntity)
    }

    override suspend fun updateNote(noteEntity: NoteEntity) {
        return notesDao.updateNotes(noteEntity)
    }

    override suspend fun deleteNote(noteEntity: NoteEntity) {
        return notesDao.deleteNote(noteEntity)
    }

    override suspend fun deleteAllNotes() {
        return notesDao.deleteAllNotes()
    }

    override suspend fun searchByQuery(query: String): Flow<List<NoteEntity>> {
        return notesDao.searchForNotes(query)
    }

    override fun getAllTags(): Flow<List<TagEntity>> {
        TODO("Not yet implemented")
    }

    override fun insertTag(tagEntity: TagEntity) {
        TODO("Not yet implemented")
    }

    override fun deleteAllTags() {
        TODO("Not yet implemented")
    }

}