package com.example.chefhub.db.repository

import com.example.chefhub.db.dao.RatingsDao
import com.example.chefhub.db.data.Ratings
import kotlinx.coroutines.flow.Flow

class RatingsRepository(private val ratingsDao: RatingsDao) {
    // Inserta una nueva calificación para una receta
    suspend fun insertRating(rating: Ratings) = ratingsDao.insertRating(rating)

    // Actualiza una calificación existente
    suspend fun updateRating(rating: Ratings) = ratingsDao.updateRating(rating)

    // Elimina una calificación específica de una receta
    suspend fun deleteRating(rating: Ratings) = ratingsDao.deleteRating(rating)

    // Obtiene todas las calificaciones de una receta específica
    fun getRatingsForRecipe(recipeId: Int): Flow<List<Ratings>> = ratingsDao.getRatingsForRecipe(recipeId)

    // Obtiene la calificación promedio de una receta específica
    fun getAverageRatingForRecipe(recipeId: Int): Flow<Double?> = ratingsDao.getAverageRatingForRecipe(recipeId)

    // Obtiene todas las calificaciones hechas por un usuario específico
    fun getRatingsByUser(userId: Int): Flow<List<Ratings>> = ratingsDao.getRatingsByUser(userId)

    // Obtiene una calificación específica por su ID
    suspend fun getRatingById(ratingId: Int): Ratings? = ratingsDao.getRatingById(ratingId)

    // Obtiene la calificación de un usuario para una receta específica
    suspend fun getRatingForUserAndRecipe(userId: Int, recipeId: Int): Ratings? = ratingsDao.getRatingForUserAndRecipe(userId, recipeId)
}
