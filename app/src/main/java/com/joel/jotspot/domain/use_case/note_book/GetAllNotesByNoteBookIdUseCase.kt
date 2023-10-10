package com.joel.jotspot.domain.use_case.note_book

import com.joel.jotspot.data.relations.NoteBookWithNotes
import com.joel.jotspot.data.repository.NoteBooksRepo
import kotlinx.coroutines.flow.Flow

class GetAllNotesByNoteBookIdUseCase(
    private val notesBooksRepo: NoteBooksRepo
) {

    operator fun invoke(noteBookId : Int) : Flow<NoteBookWithNotes>{
        return notesBooksRepo.getNotesByNoteBookId(noteBookId)
    }
}