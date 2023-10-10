package com.joel.jotspot.domain.use_case.note_book

import com.joel.jotspot.data.repository.NoteBooksRepo

class DeleteAllNoteBookUseCase(
    private val notesBooksRepo: NoteBooksRepo
) {

    suspend operator fun invoke(){
       notesBooksRepo.deleteAllNoteBooks()
    }
}