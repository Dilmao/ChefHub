package com.example.chefhub.db.repository

import com.example.chefhub.db.dao.UsersDao
import com.example.chefhub.db.data.Users
import kotlinx.coroutines.flow.Flow

class UsersRepository(private val usersDao: UsersDao) {
    // Inserta un nuevo usuario
    suspend fun insertUser(user: Users) = usersDao.insertUser(user)

    // Actualiza los datos de un usuario
    suspend fun updateUser(user: Users) = usersDao.updateUser(user)

    // Elimina un usuario
    suspend fun deleteUser(user: Users) = usersDao.deleteUser(user)

    // Obtiene todos los usuarios (como Flow para respuestas reactivas)
    fun getUsers(): Flow<List<Users>> = usersDao.getUsers()

    // Obtiene un usuario por correo electrónico
    suspend fun getUserByEmail(email: String): Users? = usersDao.getUserByEmail(email)

    // Obtiene un usuario por nombre de usuario
    suspend fun getUserByUserName(userName: String): Users? = usersDao.getUserByUserName(userName)

    // Obtiene un usuario por nombre de usuario
    fun searchUsersByUserName(userName: String): Flow<List<Users>> = usersDao.searchUsersByUserName(userName)
}
