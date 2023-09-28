package com.joel.jotspot.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.joel.jotspot.data.dao.NotesDao
import com.joel.jotspot.data.dao.TagsDao
import com.joel.jotspot.data.model.NoteEntity
import com.joel.jotspot.data.model.TagEntity

@Database(
    entities = [NoteEntity::class, TagEntity::class],
    version = 1
)
abstract class JotSpotDatabase : RoomDatabase(){

    abstract fun notesDao() : NotesDao
    abstract fun tagsDao() : TagsDao

}