package com.example.chefhub.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.chefhub.db.data.Recipes
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {
    // Inserta una receta. Si la receta ya existe, se ignora.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecipe(recipes: Recipes)

    // Actualiza una receta
    @Update
    suspend fun updateRecipe(recipes: Recipes)

    // Elimina una receta
    @Delete
    suspend fun deleteRecipe(recipes: Recipes)

    // Obtiene todas las recetas ordenadas por título
    @Query("SELECT * FROM recipes ORDER BY createdAt DESC")
    fun getRecipes(): Flow<List<Recipes>>

    // Obtiene todas las recetas asociadas a un usuario específico
    @Query("SELECT * FROM recipes WHERE userId = :userId ORDER BY createdAt DESC")
    fun getRecipesByUser(userId: Int): Flow<List<Recipes>>

    // Obtiene recetas subidas por una lista de usuarios seguidos, ordenadas por fecha de publicación.
    @Query("""
        SELECT * FROM recipes 
        WHERE userId IN (:followedUsersIds) 
        ORDER BY createdAt DESC
    """)
    fun getRecipesByFollowedUsers(followedUsersIds: List<Int>): Flow<List<Recipes>>
}
