package com.joel.jotspot.data.di

import android.app.Application
import androidx.room.Room
import com.joel.jotspot.data.dao.NoteBookDao
import com.joel.jotspot.data.dao.NotesDao
import com.joel.jotspot.data.dao.TagsDao
import com.joel.jotspot.data.db.JotSpotDatabase
import com.joel.jotspot.utils.Constants.JOT_SPOT_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    @Singleton
    fun providesRoomDatabase(application: Application) : JotSpotDatabase{
        return Room.databaseBuilder(
            application,
            JotSpotDatabase::class.java,
            JOT_SPOT_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun providesNotesDao(database: JotSpotDatabase) : NotesDao{
        return database.notesDao()
    }

    @Provides
    @Singleton
    fun providesNoteBookDao(database: JotSpotDatabase) : NoteBookDao{
        return database.noteBooksDao()
    }

    @Provides
    @Singleton
    fun providesTagsDao(database: JotSpotDatabase) : TagsDao{
        return database.tagsDao()
    }

}