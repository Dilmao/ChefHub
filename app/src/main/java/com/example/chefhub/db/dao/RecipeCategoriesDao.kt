package com.example.chefhub.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chefhub.db.data.Categories
import com.example.chefhub.db.data.RecipeCategories
import com.example.chefhub.db.data.Recipes
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeCategoriesDao {
    // Inserta una relación entre receta y categoría
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecipeCategory(recipeCategory: RecipeCategories)

    // Elimina una relación específica entre una receta y una categoría
    @Delete
    suspend fun deleteRecipeCategory(recipeCategory: RecipeCategories)

    // Elimina todas las categorías de una receta específica
    @Query("DELETE FROM recipe_categories WHERE recipeId = :recipeId")
    suspend fun deleteCategoriesForRecipe(recipeId: Int)

    // Elimina todas las recetas de una categoría específica
    @Query("DELETE FROM recipe_categories WHERE categoryId = :categoryId")
    suspend fun deleteRecipesForCategory(categoryId: Int)

    // Obtiene todas las categorías asociadas a una receta específica
    @Query("""
        SELECT c.* FROM categories c 
        INNER JOIN recipe_categories rc ON c.categoryId = rc.categoryId
        WHERE rc.recipeId = :recipeId
    """)
    fun getCategoriesForRecipe(recipeId: Int): Flow<List<Categories>>

    // Obtiene todas las recetas asociadas a una categoría específica
    @Query("""
        SELECT r.* FROM recipes r 
        INNER JOIN recipe_categories rc ON r.recipeId = rc.recipeId
        WHERE rc.categoryId = :categoryId
    """)
    fun getRecipesForCategory(categoryId: Int): Flow<List<Recipes>>

    // (Opcional) Obtiene todas las recetas de una categoría específica para un usuario específico
    @Query("""
        SELECT r.* FROM recipes r
        INNER JOIN recipe_categories rc ON r.recipeId = rc.recipeId
        WHERE rc.categoryId = :categoryId AND r.userId = :userId
    """)
    fun getRecipesForCategoryByUser(userId: Int, categoryId: Int): Flow<List<Recipes>>
}
