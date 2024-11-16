package com.example.chefhub.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.chefhub.db.data.Comments
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentsDao {

    // Inserta un comentario
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: Comments)

    // Actualiza un comentario, actualizando también la fecha de modificación
    @Update
    suspend fun updateComment(comment: Comments)

    // Elimina un comentario
    @Delete
    suspend fun deleteComment(comment: Comments)

    // Obtiene todos los comentarios de una receta específica
    @Query("""
        SELECT * FROM comments
        WHERE recipeId = :recipeId
        ORDER BY createdAt ASC
    """)
    fun getCommentsForRecipe(recipeId: Int): Flow<List<Comments>>

    // Obtiene todos los comentarios hechos por un usuario específico
    @Query("""
        SELECT * FROM comments
        WHERE userId = :userId
        ORDER BY createdAt ASC
    """)
    fun getCommentsByUser(userId: Int): Flow<List<Comments>>

    // Obtiene un comentario específico por su ID
    @Query("SELECT * FROM comments WHERE commentId = :commentId")
    suspend fun getCommentById(commentId: Int): Comments?

    // Verifica si un comentario existe
    @Query("SELECT COUNT(*) FROM comments WHERE commentId = :commentId")
    suspend fun commentExists(commentId: Int): Int

    // Cuenta el número de comentarios para una receta específica
    @Query("SELECT COUNT(*) FROM comments WHERE recipeId = :recipeId")
    suspend fun countCommentsForRecipe(recipeId: Int): Int
}
