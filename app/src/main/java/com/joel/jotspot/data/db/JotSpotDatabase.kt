package com.joel.jotspot.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.joel.jotspot.data.dao.NoteBookDao
import com.joel.jotspot.data.dao.NotesDao
import com.joel.jotspot.data.dao.TagsDao
import com.joel.jotspot.data.model.NoteEntity
import com.joel.jotspot.data.model.TagEntity
import com.joel.jotspot.data.db.converters.UriTypeConverter

@Database(
    entities = [NoteEntity::class, TagEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(UriTypeConverter::class)
abstract class JotSpotDatabase : RoomDatabase(){

    abstract fun notesDao() : NotesDao
    abstract fun tagsDao() : TagsDao
    abstract fun noteBooksDao() : NoteBookDao

}