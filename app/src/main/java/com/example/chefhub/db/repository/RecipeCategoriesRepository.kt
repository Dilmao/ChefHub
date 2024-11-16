package com.example.chefhub.db.repository

import com.example.chefhub.db.dao.RecipeCategoriesDao
import com.example.chefhub.db.data.Categories
import com.example.chefhub.db.data.RecipeCategories
import com.example.chefhub.db.data.Recipes
import kotlinx.coroutines.flow.Flow

class RecipeCategoriesRepository(private val recipeCategoriesDao: RecipeCategoriesDao) {
    // Inserta una relación entre una receta y una categoría
    suspend fun insertRecipeCategory(recipeCategory: RecipeCategories) =
        recipeCategoriesDao.insertRecipeCategory(recipeCategory)

    // Elimina una relación específica entre una receta y una categoría
    suspend fun deleteRecipeCategory(recipeCategory: RecipeCategories) =
        recipeCategoriesDao.deleteRecipeCategory(recipeCategory)

    // Elimina todas las categorías asociadas a una receta específica
    suspend fun deleteCategoriesForRecipe(recipeId: Int) =
        recipeCategoriesDao.deleteCategoriesForRecipe(recipeId)

    // Elimina todas las recetas asociadas a una categoría específica
    suspend fun deleteRecipesForCategory(categoryId: Int) =
        recipeCategoriesDao.deleteRecipesForCategory(categoryId)

    // Obtiene todas las categorías asociadas a una receta específica
    fun getCategoriesForRecipe(recipeId: Int): Flow<List<Categories>> =
        recipeCategoriesDao.getCategoriesForRecipe(recipeId)

    // Obtiene todas las recetas asociadas a una categoría específica
    fun getRecipesForCategory(categoryId: Int): Flow<List<Recipes>> =
        recipeCategoriesDao.getRecipesForCategory(categoryId)

    // (Opcional) Obtiene todas las recetas de una categoría específica para un usuario específico
    fun getRecipesForCategoryByUser(userId: Int, categoryId: Int): Flow<List<Recipes>> =
        recipeCategoriesDao.getRecipesForCategoryByUser(userId, categoryId)
}
