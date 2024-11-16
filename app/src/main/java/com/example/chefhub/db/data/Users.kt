package com.example.chefhub.db.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "users",
    indices = [
        Index(value = ["userName"], unique = true),
        Index(value = ["email"], unique = true)
    ]
)
data class Users(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,   // PK, AUTO_INCREMENT
    val userName: String,
    val email: String,
    val password: String,
    val profilePicture: String? = null,
    val bio: String? = null,
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)