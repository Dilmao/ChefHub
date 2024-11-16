package com.example.chefhub.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.chefhub.db.data.Users
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {
    // COMENTARIO
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(users: Users)

    // COMENTARIO
    @Update
    suspend fun updateUser(users: Users)

    // COMENTARIO
    @Delete
    suspend fun deleteUser(users: Users)

    // COMENTARIO
    @Query("SELECT * FROM users ORDER BY userName ASC")
    fun getUsers(): Flow<List<Users>>

    // COMENTARIO
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): Users?

    // COMENTARIO
    @Query("SELECT * FROM users WHERE userName = :userName LIMIT 1")
    suspend fun getUserByUserName(userName: String): Users?
}
