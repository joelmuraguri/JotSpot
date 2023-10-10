package com.joel.jotspot.domain.use_case.note

import com.joel.jotspot.data.repository.NotesRepo

class DeleteAllNotesUseCase(
    private val repository : NotesRepo
) {

    suspend operator fun invoke(){
        return repository.deleteAllNotes()
    }
}