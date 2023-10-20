package com.joel.jotspot.data.model

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.joel.jotspot.data.db.converters.UriTypeConverter

@Entity(
    tableName = "Notes_table",
    foreignKeys = [
        ForeignKey(
            entity = NoteBookEntity::class,
            parentColumns = ["noteBookId"],
            childColumns = ["noteBookId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class NoteEntity(
    @ColumnInfo(name = "noteBookId", index = true)
    val noteBookId : Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    @TypeConverters(UriTypeConverter::class)
    val image: Uri?,
    val timeStamp: Long,
    val isPinned : Boolean = false
)
