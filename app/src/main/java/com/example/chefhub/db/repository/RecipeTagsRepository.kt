package com.example.chefhub.db.repository

import com.example.chefhub.db.dao.RecipeTagsDao
import com.example.chefhub.db.data.RecipeTags
import com.example.chefhub.db.data.Recipes
import com.example.chefhub.db.data.Tags
import kotlinx.coroutines.flow.Flow

class RecipeTagsRepository(private val recipeTagsDao: RecipeTagsDao) {
    // Inserta una relación entre receta y etiqueta
    suspend fun insertRecipeTag(recipeTag: RecipeTags) = recipeTagsDao.insertRecipeTag(recipeTag)

    // Elimina una relación específica entre una receta y una etiqueta
    suspend fun deleteRecipeTag(recipeTag: RecipeTags) = recipeTagsDao.deleteRecipeTag(recipeTag)

    // Elimina todas las etiquetas asociadas a una receta específica
    suspend fun deleteTagsForRecipe(recipeId: Int) = recipeTagsDao.deleteTagsForRecipe(recipeId)

    // Elimina todas las recetas asociadas a una etiqueta específica
    suspend fun deleteRecipesForTag(tagId: Int) = recipeTagsDao.deleteRecipesForTag(tagId)

    // Obtiene todas las etiquetas asociadas a una receta específica
    fun getTagsForRecipe(recipeId: Int): Flow<List<Tags>> = recipeTagsDao.getTagsForRecipe(recipeId)

    // Obtiene todas las recetas asociadas a una etiqueta específica
    fun getRecipesForTag(tagId: Int): Flow<List<Recipes>> = recipeTagsDao.getRecipesForTag(tagId)
}
