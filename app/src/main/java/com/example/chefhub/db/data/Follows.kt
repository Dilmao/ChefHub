package com.example.chefhub.db.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "follows",
    primaryKeys = ["followerId", "followingId"],
    indices = [Index(value = ["followerId"]), Index(value = ["followingId"])],
    foreignKeys = [
        ForeignKey(
            entity = Users::class,
            parentColumns = ["userId"],
            childColumns = ["followerId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Users::class,
            parentColumns = ["userId"],
            childColumns = ["followingId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Follows(
    val followerId: Int = 0,
    val followingId: Int = 0,
)
