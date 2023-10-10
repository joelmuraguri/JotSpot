package com.joel.jotspot.domain.use_case.note_book

import com.joel.jotspot.data.model.NoteBookEntity
import com.joel.jotspot.data.repository.NoteBooksRepo

class UpdateNoteBookUseCase(
    private val notesBooksRepo: NoteBooksRepo
) {

    suspend operator fun invoke(noteBookEntity: NoteBookEntity){
        return notesBooksRepo.updateNoteBook(noteBookEntity)
    }
}