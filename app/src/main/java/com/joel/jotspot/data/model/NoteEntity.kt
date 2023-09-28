package com.joel.jotspot.data.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notes_table")
data class NoteEntity(
    @PrimaryKey val id : Int,
    val title : String,
    val content : String,
    val image : Uri,
    val timeStamp : Long
)
