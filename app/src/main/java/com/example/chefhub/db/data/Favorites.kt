package com.example.chefhub.db.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "favorites",
    primaryKeys = ["userId", "recipeId"],
    indices = [Index(value = ["userId"]), Index(value = ["recipeId"])],
    foreignKeys = [
        ForeignKey(
            entity = Users::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Recipes::class,
            parentColumns = ["recipeId"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Favorites(
    val userId: Int = 0,
    val recipeId: Int = 0,
)
