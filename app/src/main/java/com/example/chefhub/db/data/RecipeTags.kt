package com.example.chefhub.db.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "recipe_tags",
    primaryKeys = ["recipeId", "tagId"],
    indices = [Index(value = ["recipeId"]), Index(value = ["tagId"])],
    foreignKeys = [
        ForeignKey(
            entity = Recipes::class,
            parentColumns = ["recipeId"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Tags::class,
            parentColumns = ["tagId"],
            childColumns = ["tagId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RecipeTags(
    val recipeId: Int = 0,
    val tagId: Int = 0,
)