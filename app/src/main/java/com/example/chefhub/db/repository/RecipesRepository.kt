package com.example.chefhub.db.repository

import com.example.chefhub.db.dao.RecipesDao
import com.example.chefhub.db.data.Recipes
import kotlinx.coroutines.flow.Flow

class RecipesRepository(private val recipesDao: RecipesDao) {
    // Inserta una nueva receta en la base de datos
    suspend fun insertRecipe(recipes: Recipes) = recipesDao.insertRecipe(recipes)

    // Actualiza una receta existente
    suspend fun updateRecipe(recipes: Recipes) = recipesDao.updateRecipe(recipes)

    // Elimina una receta específica
    suspend fun deleteRecipe(recipes: Recipes) = recipesDao.deleteRecipe(recipes)

    // Obtiene todas las recetas ordenadas por título
    fun getRecipes(): Flow<List<Recipes>> = recipesDao.getRecipes()

    // Obtiene todas las recetas asociadas a un usuario específico
    fun getRecipesByUser(userId: Int): Flow<List<Recipes>> = recipesDao.getRecipesByUser(userId)

    // Obtiene todas las recetas asociadas a un usuario específico
    fun searchRecipesByTitle(title: String): Flow<List<Recipes>> = recipesDao.searchRecipesByTitle(title)
}
