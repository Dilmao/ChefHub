package com.example.chefhub.db.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "tags",
    indices = [Index(value = ["tagName"], unique = true)]
)
data class Tags(
    @PrimaryKey(autoGenerate = true) val tagId: Int = 0,   // PK, AUTO_INCREMENT
    val tagName: String,
)