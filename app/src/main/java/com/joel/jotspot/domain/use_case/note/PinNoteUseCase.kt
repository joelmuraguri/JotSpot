package com.joel.jotspot.domain.use_case.note

import com.joel.jotspot.data.repository.NotesRepo

class PinNoteUseCase(
    private val notesRepo: NotesRepo
) {

    suspend operator fun  invoke(noteId : Int, isPinned : Boolean){
        return notesRepo.pinNote(noteId, isPinned)
    }

}