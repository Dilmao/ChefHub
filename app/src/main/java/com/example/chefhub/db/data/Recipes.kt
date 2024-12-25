package com.example.chefhub.db.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index
import androidx.room.TypeConverters
import com.example.chefhub.db.Converters

@Entity(
    tableName = "recipes",
    indices = [Index(value = ["userId"])],
    foreignKeys = [
        ForeignKey(
            entity = Users::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Categories::class,
            parentColumns = ["categoryName"],
            childColumns = ["categoryName"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Recipes(
    @PrimaryKey(autoGenerate = true) var recipeId: Int = 0, // PK, AUTO_INCREMENT
    val userId: Int = -1,
    val categoryName: String? = null,
    val title: String = "",
    val description: String? = null,
    val dificulty: String? = null,
    @TypeConverters(Converters::class) val ingredients: ArrayList<String> = arrayListOf(""),
    @TypeConverters(Converters::class) val instructions: ArrayList<String> = arrayListOf(""),
    val imageUrl: String? = null,
    val prepTime: Int? = null,
    val cookTime: Int? = null,
    val servings: Int? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)