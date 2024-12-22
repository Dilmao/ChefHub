package com.example.chefhub.db.repository

import com.example.chefhub.db.dao.CategoriesDao
import com.example.chefhub.db.data.Categories
import kotlinx.coroutines.flow.Flow

class CategoriesRepository(private val categoriesDao: CategoriesDao) {
    // Inserta una categoría en la base de datos
    suspend fun insertCategory(category: Categories) = categoriesDao.insertCategory(category)

    // Actualiza una categoría existente
    suspend fun updateCategory(category: Categories) = categoriesDao.updateCategory(category)

    // Elimina una categoría específica
    suspend fun deleteCategory(category: Categories) = categoriesDao.deleteCategory(category)

    // Obtiene todas las categorías ordenadas alfabéticamente
    fun getCategories(): Flow<List<Categories>> = categoriesDao.getCategories()

    // Obtiene una categoría por nombre
    suspend fun getCategoryByName(categoryName: String): Categories? = categoriesDao.getCategoryByName(categoryName)

    // Elimina todas las categorías (tener cuidado con las dependencias)
    suspend fun deleteAllCategories() = categoriesDao.deleteAllCategories()

    // Verifica si una categoría ya existe por su nombre
    suspend fun categoryExists(categoryName: String): Boolean {
        return categoriesDao.getCategoryByName(categoryName) != null
    }

    // COMENTARIO.
    fun searchCategoriesByName(categoryName: String): Flow<List<Categories>> = categoriesDao.searchCategoriesByName(categoryName)
}
