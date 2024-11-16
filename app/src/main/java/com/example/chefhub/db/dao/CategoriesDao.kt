package com.example.chefhub.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.chefhub.db.data.Categories
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao {
    // Inserta una nueva categoría, ignorando si ya existe
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: Categories)

    // Actualiza una categoría
    @Update
    suspend fun updateCategory(category: Categories)

    // Elimina una categoría
    @Delete
    suspend fun deleteCategory(category: Categories)

    // Obtiene todas las categorías ordenadas alfabéticamente
    @Query("SELECT * FROM categories ORDER BY categoryName ASC")
    fun getCategories(): Flow<List<Categories>>

    // Obtiene una categoría por nombre
    @Query("SELECT * FROM categories WHERE categoryName = :categoryName")
    suspend fun getCategoryByName(categoryName: String): Categories?

    // Elimina todas las categorías
    @Query("DELETE FROM categories")
    suspend fun deleteAllCategories()
}