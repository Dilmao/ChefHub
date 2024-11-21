package com.example.chefhub.db.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "categories",
    indices = [Index(value = ["categoryName"], unique = true)]
)
data class Categories(
    @PrimaryKey(autoGenerate = true) val categoryId: Int = 0,   // PK, AUTO_INCREMENT
    val categoryName: String = "",
)