package com.example.chefhub.db.repository

import com.example.chefhub.db.dao.FavoritesDao
import com.example.chefhub.db.data.Favorites
import com.example.chefhub.db.data.Recipes
import com.example.chefhub.db.data.Users
import kotlinx.coroutines.flow.Flow

class FavoritesRepository(private val favoritesDao: FavoritesDao) {
    // Inserta un favorito para un usuario y una receta
    suspend fun insertFavorite(favorite: Favorites) = favoritesDao.insertFavorite(favorite)

    // Elimina una relación de favorito específica entre un usuario y una receta
    suspend fun deleteFavorite(favorite: Favorites) = favoritesDao.deleteFavorite(favorite)

    // Elimina todos los favoritos de un usuario específico
    suspend fun deleteFavoritesForUser(userId: Int) = favoritesDao.deleteFavoritesForUser(userId)

    // Obtiene todas las recetas que un usuario ha marcado como favoritas
    fun getFavoritesForUser(userId: Int): Flow<List<Recipes>> = favoritesDao.getFavoritesForUser(userId)

    // Obtiene todos los usuarios que han marcado una receta específica como favorita
    fun getUsersForFavoriteRecipe(recipeId: Int): Flow<List<Users>> = favoritesDao.getUsersForFavoriteRecipe(recipeId)

    // Verifica si un usuario ya ha marcado una receta como favorita
    suspend fun isRecipeFavorite(userId: Int, recipeId: Int): Int = favoritesDao.isRecipeFavorite(userId, recipeId)
}
