package com.joel.jotspot.domain.use_case.note

import com.joel.jotspot.data.repository.JotSpotRepository

class DeleteAllNotesUseCase(
    private val repository : JotSpotRepository
) {

    suspend operator fun invoke(){
        return repository.deleteAllNotes()
    }
}