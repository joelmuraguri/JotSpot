package com.joel.jotspot.data.repository.impl

import com.joel.jotspot.data.dao.NotesDao
import com.joel.jotspot.data.model.NoteEntity
import com.joel.jotspot.data.repository.NotesRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesRepository @Inject constructor(
    private val notesDao: NotesDao,
) : NotesRepo {

    override val allNotes: Flow<List<NoteEntity>> = notesDao.getAllNotes()
    override fun getPinnedNotesForNotebook(noteBookId: Int): Flow<List<NoteEntity>> {
        return notesDao.getPinnedNotesForNotebook(noteBookId)
    }

    override fun getUnPinnedNotesForNotebook(noteBookId: Int): Flow<List<NoteEntity>> {
        return notesDao.getUnpinnedNotesForNotebook(noteBookId)
    }

    override fun getNoteById(id: Int): Flow<NoteEntity> {
        return notesDao.getNoteById(id)
    }

    override suspend fun insertNote(noteEntity: NoteEntity) {
        return notesDao.insertNote(noteEntity)
    }

    override suspend fun updateNote(noteEntity: NoteEntity) {
        return notesDao.updateNotes(noteEntity)
    }

    override suspend fun pinNote(noteId: Int, isPinned: Boolean) {
        return notesDao.pinNote(noteId, true)
    }

    override suspend fun unPinNote(noteId: Int, isPinned: Boolean) {
        return notesDao.pinNote(noteId, false)
    }

    override suspend fun deleteNote(noteEntity: NoteEntity) {
        return notesDao.deleteNote(noteEntity)
    }

    override suspend fun deleteAllNotes() {
        return notesDao.deleteAllNotes()
    }


}