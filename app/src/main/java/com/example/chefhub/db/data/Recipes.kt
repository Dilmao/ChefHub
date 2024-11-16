package com.example.chefhub.db.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "recipes",
    indices = [Index(value = ["userId"])],
    foreignKeys = [
        ForeignKey(
            entity = Users::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Recipes(
    @PrimaryKey(autoGenerate = true) val recipeId: Int = 0, // PK, AUTO_INCREMENT
    val userId: Int,
    val title: String,
    val description: String? = null,
    val ingredientList: String,
    val instructions: String,
    val imageUrl: String? = null,
    val prepTime: Int? = null,
    val cookTime: Int? = null,
    val servings: Int? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)