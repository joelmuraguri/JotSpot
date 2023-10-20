package com.joel.jotspot.domain.use_case.note

import com.joel.jotspot.data.model.NoteEntity
import com.joel.jotspot.data.repository.NotesRepo
import kotlinx.coroutines.flow.Flow

class GetPinnedNotesUseCase(
    private val notesRepo: NotesRepo
) {

    operator fun invoke(noteBookId : Int) : Flow<List<NoteEntity>>{
        return notesRepo.getPinnedNotesForNotebook(noteBookId)
    }
}