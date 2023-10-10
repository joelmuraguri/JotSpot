package com.joel.jotspot.domain.use_case.note

import com.joel.jotspot.data.model.NoteEntity
import com.joel.jotspot.data.repository.NotesRepo
import kotlinx.coroutines.flow.Flow

class GetNotesUseCase(
    private val repository: NotesRepo
) {

    operator fun invoke(): Flow<List<NoteEntity>> {
        return repository.allNotes
    }
}