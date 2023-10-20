package com.joel.jotspot.domain.use_case.note

data class NoteUseCases(
    val getNotesUseCase: GetNotesUseCase,
    val getNoteByIdUseCase: GetNoteByIdUseCase,
    val insertNoteUseCase: InsertNoteUseCase,
    val updateNotesUseCase: UpdateNoteUseCase,
    val deleteAllNotesUseCase: DeleteAllNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val pinNoteUseCase: PinNoteUseCase,
    val unPinNoteUseCase: UnPinNoteUseCase,
    val getPinnedNotesUseCase: GetPinnedNotesUseCase,
    val getUnPinnedNotesUseCase: GetUnPinnedNotesUseCase
)
