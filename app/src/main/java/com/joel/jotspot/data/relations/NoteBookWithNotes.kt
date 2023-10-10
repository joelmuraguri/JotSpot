package com.joel.jotspot.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.joel.jotspot.data.model.NoteBookEntity
import com.joel.jotspot.data.model.NoteEntity

data class NoteBookWithNotes(
    @Embedded val noteBook: NoteBookEntity,
    @Relation(
        parentColumn = "noteBookId",
        entityColumn = "noteBookId",
    )
    val notes : List<NoteEntity>
)
