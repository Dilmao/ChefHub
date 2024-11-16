package com.example.chefhub.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chefhub.db.data.Follows
import com.example.chefhub.db.data.Users
import kotlinx.coroutines.flow.Flow

@Dao
interface FollowsDao {
    // Inserta una relación entre seguidor y seguido
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFollower(follow: Follows)

    // Elimina una relación específica entre un seguidor y un seguido
    @Delete
    suspend fun deleteFollower(follow: Follows)

    // Elimina todas las relaciones de seguimiento de un seguidor específico
    @Query("DELETE FROM follows WHERE followerId = :followerId")
    suspend fun deleteFollowsForFollower(followerId: Int)

    // Obtiene todos los seguidores de un usuario específico
    @Query("""
        SELECT u.* FROM users u
        INNER JOIN follows f ON u.userId = f.followerId
        WHERE f.followingId = :followingId
    """)
    fun getFollowersForUser(followingId: Int): Flow<List<Users>>

    // Obtiene todos los usuarios seguidos por un seguidor específico
    @Query("""
        SELECT u.* FROM users u
        INNER JOIN follows f ON u.userId = f.followingId
        WHERE f.followerId = :followerId
    """)
    fun getFollowingForUser(followerId: Int): Flow<List<Users>>

    // Verifica si un usuario sigue a otro
    @Query("""
        SELECT COUNT(*) FROM follows 
        WHERE followerId = :followerId AND followingId = :followingId
    """)
    suspend fun isUserFollowing(followerId: Int, followingId: Int): Int
}
