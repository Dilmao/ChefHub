package com.example.chefhub.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.chefhub.db.data.Tags
import kotlinx.coroutines.flow.Flow

@Dao
interface TagsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(tags: Tags)

    @Update
    suspend fun updateTag(tags: Tags)

    @Delete
    suspend fun deleteTag(tags: Tags)

    @Query("SELECT * FROM tags ORDER BY tagName ASC")
    fun getTags(): Flow<List<Tags>>

    @Query("SELECT * FROM tags WHERE tagName = :tagName LIMIT 1")
    suspend fun getTagByName(tagName: String): Tags?
}
