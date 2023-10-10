package com.joel.jotspot.domain.use_case.note_book

data class NoteBookUseCases(
    val getAllNoteBooks: GetAllNoteBooksUseCase,
    val getNoteBookById: GetNoteBookByIdUseCase,
    val getAllNotesByNoteBookIdUseCase: GetAllNotesByNoteBookIdUseCase,
    val insertNoteBookUseCase: InsertNoteBookUseCase,
    val updateNoteBookUseCase: UpdateNoteBookUseCase,
    val deleteNoteBookUseCase: DeleteNoteBookUseCase,
    val deleteAllNoteBookUseCase: DeleteAllNoteBookUseCase
)
