package com.example.chefhub.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chefhub.db.data.Favorites
import com.example.chefhub.db.data.Recipes
import com.example.chefhub.db.data.Users
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    // Inserta una relación entre usuario y receta
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(favorite: Favorites)

    // Elimina una relación específica entre un usuario y una receta
    @Delete
    suspend fun deleteFavorite(favorite: Favorites)

    // Elimina todos los favoritos de un usuario específico
    @Query("DELETE FROM favorites WHERE userId = :userId")
    suspend fun deleteFavoritesForUser(userId: Int)

    // Obtiene todas las recetas favoritas de un usuario específico
    @Query("""
        SELECT r.* FROM recipes r
        INNER JOIN favorites f ON r.recipeId = f.recipeId
        WHERE f.userId = :userId
    """)
    fun getFavoritesForUser(userId: Int): Flow<List<Recipes>>

    // Obtiene todos los usuarios que marcaron una receta específica como favorita
    @Query("""
        SELECT u.* FROM users u
        INNER JOIN favorites f ON u.userId = f.userId
        WHERE f.recipeId = :recipeId
    """)
    fun getUsersForFavoriteRecipe(recipeId: Int): Flow<List<Users>>

    // Verifica si un usuario ya ha marcado una receta como favorita
    @Query("""
        SELECT COUNT(*) FROM favorites
        WHERE userId = :userId AND recipeId = :recipeId
    """)
    suspend fun isRecipeFavorite(userId: Int, recipeId: Int): Int
}
