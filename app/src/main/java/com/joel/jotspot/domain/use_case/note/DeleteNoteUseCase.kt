package com.joel.jotspot.domain.use_case.note

import com.joel.jotspot.data.model.NoteEntity
import com.joel.jotspot.data.repository.JotSpotRepository

class DeleteNoteUseCase(
    private val repository : JotSpotRepository
) {

    suspend operator fun invoke(noteEntity : NoteEntity){
        return repository.deleteNote(noteEntity)
    }
}