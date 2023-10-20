package com.joel.jotspot.data.di

import com.joel.jotspot.data.dao.NoteBookDao
import com.joel.jotspot.data.dao.NotesDao
import com.joel.jotspot.data.repository.NoteBooksRepo
import com.joel.jotspot.data.repository.NotesRepo
import com.joel.jotspot.data.repository.impl.NoteBookRepository
import com.joel.jotspot.data.repository.impl.NotesRepository
import com.joel.jotspot.domain.use_case.note.DeleteAllNotesUseCase
import com.joel.jotspot.domain.use_case.note.DeleteNoteUseCase
import com.joel.jotspot.domain.use_case.note.GetNoteByIdUseCase
import com.joel.jotspot.domain.use_case.note.GetNotesUseCase
import com.joel.jotspot.domain.use_case.note.InsertNoteUseCase
import com.joel.jotspot.domain.use_case.note.NoteUseCases
import com.joel.jotspot.domain.use_case.note.PinNoteUseCase
import com.joel.jotspot.domain.use_case.note.UnPinNoteUseCase
import com.joel.jotspot.domain.use_case.note.UpdateNoteUseCase
import com.joel.jotspot.domain.use_case.note_book.DeleteAllNoteBookUseCase
import com.joel.jotspot.domain.use_case.note_book.DeleteNoteBookUseCase
import com.joel.jotspot.domain.use_case.note_book.GetAllNoteBooksUseCase
import com.joel.jotspot.domain.use_case.note_book.GetAllNotesByNoteBookIdUseCase
import com.joel.jotspot.domain.use_case.note_book.GetNoteBookByIdUseCase
import com.joel.jotspot.domain.use_case.note_book.InsertNoteBookUseCase
import com.joel.jotspot.domain.use_case.note_book.NoteBookUseCases
import com.joel.jotspot.domain.use_case.note_book.UpdateNoteBookUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesNotesRepository(notesDao: NotesDao) : NotesRepo{
        return NotesRepository(notesDao)
    }

    @Provides
    @Singleton
    fun providesNoteBookRepository(noteBookDao: NoteBookDao) : NoteBooksRepo{
        return NoteBookRepository(noteBookDao)
    }


    @Provides
    @Singleton
    fun providesNotesUseCase(notesRepo: NotesRepo) : NoteUseCases{
        return NoteUseCases(
            getNotesUseCase = GetNotesUseCase(notesRepo),
            getNoteByIdUseCase = GetNoteByIdUseCase(notesRepo),
            insertNoteUseCase = InsertNoteUseCase(notesRepo),
            updateNotesUseCase = UpdateNoteUseCase(notesRepo),
            deleteNoteUseCase = DeleteNoteUseCase(notesRepo),
            deleteAllNotesUseCase = DeleteAllNotesUseCase(notesRepo),
            unPinNoteUseCase = UnPinNoteUseCase(notesRepo),
            pinNoteUseCase = PinNoteUseCase(notesRepo)
        )
    }

    @Provides
    @Singleton
    fun providesNoteBookUseCases(noteBooksRepo: NoteBooksRepo) : NoteBookUseCases{
        return NoteBookUseCases(
            getAllNoteBooks = GetAllNoteBooksUseCase(noteBooksRepo),
            getNoteBookById = GetNoteBookByIdUseCase(noteBooksRepo),
            getAllNotesByNoteBookIdUseCase = GetAllNotesByNoteBookIdUseCase(noteBooksRepo),
            insertNoteBookUseCase = InsertNoteBookUseCase(noteBooksRepo),
            updateNoteBookUseCase = UpdateNoteBookUseCase(noteBooksRepo),
            deleteNoteBookUseCase = DeleteNoteBookUseCase(noteBooksRepo),
            deleteAllNoteBookUseCase = DeleteAllNoteBookUseCase(noteBooksRepo)
        )
    }

}