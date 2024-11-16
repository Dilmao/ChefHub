package com.example.chefhub.db.repository

import com.example.chefhub.db.dao.CommentsDao
import com.example.chefhub.db.data.Comments
import kotlinx.coroutines.flow.Flow

class CommentsRepository(private val commentsDao: CommentsDao) {
    // Inserta un comentario en la base de datos
    suspend fun insertComment(comment: Comments) = commentsDao.insertComment(comment)

    // Actualiza un comentario existente
    suspend fun updateComment(comment: Comments) = commentsDao.updateComment(comment)

    // Elimina un comentario de la base de datos
    suspend fun deleteComment(comment: Comments) = commentsDao.deleteComment(comment)

    // Obtiene todos los comentarios para una receta específica
    fun getCommentsForRecipe(recipeId: Int): Flow<List<Comments>> = commentsDao.getCommentsForRecipe(recipeId)

    // Obtiene todos los comentarios realizados por un usuario específico
    fun getCommentsByUser(userId: Int): Flow<List<Comments>> = commentsDao.getCommentsByUser(userId)

    // Obtiene un comentario específico por su ID
    suspend fun getCommentById(commentId: Int): Comments? = commentsDao.getCommentById(commentId)

    // Verifica si un comentario existe en la base de datos por su ID
    suspend fun commentExists(commentId: Int): Int = commentsDao.commentExists(commentId)

    // Cuenta la cantidad de comentarios para una receta específica
    suspend fun countCommentsForRecipe(recipeId: Int): Int = commentsDao.countCommentsForRecipe(recipeId)
}
