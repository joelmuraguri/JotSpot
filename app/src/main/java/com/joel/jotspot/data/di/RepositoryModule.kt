package com.joel.jotspot.data.di

import com.joel.jotspot.data.dao.NotesDao
import com.joel.jotspot.data.dao.TagsDao
import com.joel.jotspot.data.repository.JotSpotRepository
import com.joel.jotspot.data.repository.JotSpotRepositoryImpl
import com.joel.jotspot.domain.use_case.note.DeleteAllNotesUseCase
import com.joel.jotspot.domain.use_case.note.DeleteNoteUseCase
import com.joel.jotspot.domain.use_case.note.GetNoteByIdUseCase
import com.joel.jotspot.domain.use_case.note.GetNotesUseCase
import com.joel.jotspot.domain.use_case.note.InsertNoteUseCase
import com.joel.jotspot.domain.use_case.note.NoteUseCases
import com.joel.jotspot.domain.use_case.note.UpdateNoteUseCase
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
    fun providesJotSpotRepository(notesDao: NotesDao,tagsDao: TagsDao) : JotSpotRepository{
        return JotSpotRepositoryImpl(notesDao,tagsDao)
    }

    @Provides
    @Singleton
    fun providesNotesUseCase(jotSpotRepository: JotSpotRepository) : NoteUseCases{
        return NoteUseCases(
            getNotesUseCase = GetNotesUseCase(jotSpotRepository),
            getNoteByIdUseCase = GetNoteByIdUseCase(jotSpotRepository),
            insertNoteUseCase = InsertNoteUseCase(jotSpotRepository),
            updateNotesUseCase = UpdateNoteUseCase(jotSpotRepository),
            deleteNoteUseCase = DeleteNoteUseCase(jotSpotRepository),
            deleteAllNotesUseCase = DeleteAllNotesUseCase(jotSpotRepository)
        )
    }


}