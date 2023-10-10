package com.joel.jotspot.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("NoteBook_Table")
data class NoteBookEntity(
    @PrimaryKey(autoGenerate = true) val noteBookId : Int,
    val title : String,
)
