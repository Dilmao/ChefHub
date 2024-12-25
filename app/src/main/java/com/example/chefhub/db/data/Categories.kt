package com.example.chefhub.db.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "categories"
)
data class Categories(
    @PrimaryKey val categoryName: String // PK
)