package com.joel.jotspot.domain.use_case.note

import com.joel.jotspot.data.model.NoteEntity
import com.joel.jotspot.data.repository.NotesRepo

class InsertNoteUseCase(
    private val repository: NotesRepo
) {

    suspend operator fun invoke(noteEntity: NoteEntity) {
        return repository.insertNote(noteEntity)
    }
}