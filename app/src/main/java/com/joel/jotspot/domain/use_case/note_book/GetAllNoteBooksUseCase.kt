package com.joel.jotspot.domain.use_case.note_book

import com.joel.jotspot.data.model.NoteBookEntity
import com.joel.jotspot.data.repository.NoteBooksRepo
import kotlinx.coroutines.flow.Flow

class GetAllNoteBooksUseCase(
    private val noteBookRepo: NoteBooksRepo
){

    operator fun invoke() : Flow<List<NoteBookEntity>>{
        return noteBookRepo.allNoteBooks
    }
}
