package com.joel.jotspot.domain.use_case.note_book

import com.joel.jotspot.data.model.NoteBookEntity
import com.joel.jotspot.data.repository.NoteBooksRepo
import kotlinx.coroutines.flow.Flow

class GetNoteBookByIdUseCase(
    private val noteBooksRepo: NoteBooksRepo
) {

    operator fun invoke(noteBookId : Int) : Flow<NoteBookEntity>{
        return noteBooksRepo.getNoteBookById(noteBookId)
    }

}