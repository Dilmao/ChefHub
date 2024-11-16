package com.example.chefhub.db.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "recipe_categories",
    primaryKeys = ["recipeId", "categoryId"],
    indices = [Index(value = ["recipeId"]), Index(value = ["categoryId"])],
    foreignKeys = [
        ForeignKey(
            entity = Recipes::class,
            parentColumns = ["recipeId"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Categories::class,
            parentColumns = ["categoryId"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RecipeCategories(
    val recipeId: Int = 0,
    val categoryId: Int = 0,
)