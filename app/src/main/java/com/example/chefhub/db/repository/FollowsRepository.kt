package com.example.chefhub.db.repository

import com.example.chefhub.db.dao.FollowsDao
import com.example.chefhub.db.data.Follows
import com.example.chefhub.db.data.Users
import kotlinx.coroutines.flow.Flow

class FollowsRepository(private val followsDao: FollowsDao) {
    // Inserta una relación de seguimiento entre un usuario y otro
    suspend fun insertFollower(follow: Follows) = followsDao.insertFollower(follow)

    // Elimina una relación de seguimiento específica entre un seguidor y un seguido
    suspend fun deleteFollower(follow: Follows) = followsDao.deleteFollower(follow)

    // Elimina todas las relaciones de seguimiento de un seguidor específico
    suspend fun deleteFollowsForFollower(followerId: Int) = followsDao.deleteFollowsForFollower(followerId)

    // Obtiene todos los seguidores de un usuario específico
    fun getFollowersForUser(followingId: Int): Flow<List<Users>> = followsDao.getFollowersForUser(followingId)

    // Obtiene todos los usuarios seguidos por un seguidor específico
    fun getFollowingForUser(followerId: Int): Flow<List<Users>> = followsDao.getFollowingForUser(followerId)

    // Verifica si un usuario sigue a otro. Devuelve el número de coincidencias
    suspend fun isUserFollowing(followerId: Int, followingId: Int): Int = followsDao.isUserFollowing(followerId, followingId)
}
