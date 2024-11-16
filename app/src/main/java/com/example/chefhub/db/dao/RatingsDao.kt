package com.example.chefhub.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.chefhub.db.data.Ratings
import kotlinx.coroutines.flow.Flow

@Dao
interface RatingsDao {
    // Inserta una calificación
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRating(rating: Ratings)

    // Actualiza una calificación
    @Update
    suspend fun updateRating(rating: Ratings)

    // Elimina una calificación
    @Delete
    suspend fun deleteRating(rating: Ratings)

    // Obtiene todas las calificaciones de una receta específica
    @Query("""
        SELECT * FROM ratings
        WHERE recipeId = :recipeId
        ORDER BY createdAt ASC
    """)
    fun getRatingsForRecipe(recipeId: Int): Flow<List<Ratings>>

    // Obtiene la calificación promedio de una receta específica
    @Query("""
        SELECT AVG(rating) FROM ratings
        WHERE recipeId = :recipeId
    """)
    fun getAverageRatingForRecipe(recipeId: Int): Flow<Double?>

    // Obtiene todas las calificaciones hechas por un usuario específico
    @Query("""
        SELECT * FROM ratings
        WHERE userId = :userId
        ORDER BY createdAt ASC
    """)
    fun getRatingsByUser(userId: Int): Flow<List<Ratings>>

    // Obtiene una calificación específica por su ID
    @Query("SELECT * FROM ratings WHERE ratingId = :ratingId")
    suspend fun getRatingById(ratingId: Int): Ratings?

    // Obtiene la calificación de un usuario para una receta específica
    @Query("""
        SELECT * FROM ratings
        WHERE userId = :userId AND recipeId = :recipeId
    """)
    suspend fun getRatingForUserAndRecipe(userId: Int, recipeId: Int): Ratings?
}
