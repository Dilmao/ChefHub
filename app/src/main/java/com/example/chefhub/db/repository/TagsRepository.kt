package com.example.chefhub.db.repository

import com.example.chefhub.db.dao.TagsDao
import com.example.chefhub.db.data.Tags
import kotlinx.coroutines.flow.Flow

class TagsRepository(private val tagsDao: TagsDao) {
    // Inserta una nueva etiqueta
    suspend fun insertTag(tag: Tags) = tagsDao.insertTag(tag)

    // Actualiza una etiqueta existente
    suspend fun updateTag(tag: Tags) = tagsDao.updateTag(tag)

    // Elimina una etiqueta
    suspend fun deleteTag(tag: Tags) = tagsDao.deleteTag(tag)

    // Obtiene todas las etiquetas
    fun getTags(): Flow<List<Tags>> = tagsDao.getTags()

    // Obtiene una etiqueta por su nombre
    suspend fun getTagByName(tagName: String): Tags? = tagsDao.getTagByName(tagName)
}
