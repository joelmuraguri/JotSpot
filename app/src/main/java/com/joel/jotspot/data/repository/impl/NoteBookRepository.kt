package com.joel.jotspot.data.repository.impl

import com.joel.jotspot.data.dao.NoteBookDao
import com.joel.jotspot.data.model.NoteBookEntity
import com.joel.jotspot.data.relations.NoteBookWithNotes
import com.joel.jotspot.data.repository.NoteBooksRepo
import kotlinx.coroutines.flow.Flow

class NoteBookRepository(
    private val noteBookDao: NoteBookDao
) : NoteBooksRepo {

    override val allNoteBooks: Flow<List<NoteBookEntity>>
        get() = noteBookDao.getAllNotes()

    override fun getNoteBookById(noteBookId: Int): Flow<NoteBookEntity> {
        return noteBookDao.getNoteBookById(noteBookId)
    }

    override fun getNotesByNoteBookId(noteBookId: Int): Flow<NoteBookWithNotes> {
        return noteBookDao.getNoteBookNotesById(noteBookId)
    }

    override suspend fun insertNoteBook(noteBookEntity: NoteBookEntity) {
        noteBookDao.insertNoteBook(noteBookEntity)
    }

    override suspend fun updateNoteBook(noteBookEntity: NoteBookEntity) {
        noteBookDao.updateNoteBook(noteBookEntity)
    }

    override suspend fun deleteNoteBook(noteBookEntity: NoteBookEntity) {
        noteBookDao.deleteNoteBook(noteBookEntity)
    }

    override suspend fun deleteAllNoteBooks() {
        noteBookDao.deleteAllNoteBooks()
    }
}