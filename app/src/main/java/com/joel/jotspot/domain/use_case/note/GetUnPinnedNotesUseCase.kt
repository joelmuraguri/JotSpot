package com.joel.jotspot.domain.use_case.note

import com.joel.jotspot.data.model.NoteEntity
import com.joel.jotspot.data.repository.NotesRepo
import kotlinx.coroutines.flow.Flow

class GetUnPinnedNotesUseCase(
    private val notesRepo: NotesRepo
) {

    operator fun invoke(noteBookId : Int) : Flow<List<NoteEntity>>{
        return notesRepo.getUnPinnedNotesForNotebook(noteBookId)
    }
}