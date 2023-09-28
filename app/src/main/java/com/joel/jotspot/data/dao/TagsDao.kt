package com.joel.jotspot.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.joel.jotspot.data.model.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TagsDao {

    @Query("SELECT * FROM TAGS_TABLE")
    fun getAllTags() : Flow<List<TagEntity>>

    @Insert
    suspend fun insertTag(tags: TagEntity)

    @Query("DELETE FROM TAGS_TABLE")
    suspend fun deleteAllTags()

    @Delete
    suspend fun deleteTag(tags: TagEntity)

}