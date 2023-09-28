package com.joel.jotspot.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tags_table")
data class TagEntity(
    @PrimaryKey val id : Int,
    val name : String
)
