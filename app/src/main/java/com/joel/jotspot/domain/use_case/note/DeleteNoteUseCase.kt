package com.joel.jotspot.domain.use_case.note

import com.joel.jotspot.data.model.NoteEntity
import com.joel.jotspot.data.repository.NotesRepo

class DeleteNoteUseCase(
    private val repository : NotesRepo
) {

    suspend operator fun invoke(noteEntity : NoteEntity){
        return repository.deleteNote(noteEntity)
    }
}