package com.example.chefhub.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete
import com.example.chefhub.db.data.RecipeTags
import com.example.chefhub.db.data.Recipes
import com.example.chefhub.db.data.Tags
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeTagsDao {
    // Inserta una relación entre receta y etiqueta
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeTag(recipeTag: RecipeTags)

    // Elimina una relación específica entre una receta y una etiqueta
    @Delete
    suspend fun deleteRecipeTag(recipeTag: RecipeTags)

    // Elimina todas las etiquetas de una receta específica
    @Query("DELETE FROM recipe_tags WHERE recipeId = :recipeId")
    suspend fun deleteTagsForRecipe(recipeId: Int)

    // Elimina todas las recetas de una etiqueta específica
    @Query("DELETE FROM recipe_tags WHERE tagId = :tagId")
    suspend fun deleteRecipesForTag(tagId: Int)

    // Obtiene todas las etiquetas asociadas a una receta específica
    @Query(""" 
        SELECT t.* FROM tags t
        INNER JOIN recipe_tags rt ON t.tagId = rt.tagId
        WHERE rt.recipeId = :recipeId
    """)
    fun getTagsForRecipe(recipeId: Int): Flow<List<Tags>>

    // Obtiene todas las recetas asociadas a una etiqueta específica
    @Query("""
        SELECT r.* FROM recipes r
        INNER JOIN recipe_tags rt ON r.recipeId = rt.recipeId
        WHERE rt.tagId = :tagId
    """)
    fun getRecipesForTag(tagId: Int): Flow<List<Recipes>>
}
