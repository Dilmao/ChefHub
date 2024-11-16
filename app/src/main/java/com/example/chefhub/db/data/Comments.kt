package com.example.chefhub.db.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "comments",
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
data class Comments(
    @PrimaryKey(autoGenerate = true) val commentId: Int = 0,   // PK, AUTO_INCREMENT
    val userId: Int,
    val recipeId: Int,
    val commentText: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
