package com.joel.jotspot.domain.use_case.note

data class NoteUseCases(
    val getNotesUseCase: GetNotesUseCase,
    val getNoteByIdUseCase: GetNoteByIdUseCase,
    val insertNoteUseCase: InsertNoteUseCase,
    val updateNotesUseCase: GetNotesUseCase,
    val deleteAllNotesUseCase: DeleteAllNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase
)
