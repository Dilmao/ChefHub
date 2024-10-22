package com.example.chefhub.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataUser(context: Context): SQLiteOpenHelper(context, "chefhub.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""CREATE TABLE Users (
                User_id INTEGER PRIMARY KEY AUTOINCREMENT,
                Username VARCHAR(50) UNIQUE NOT NULL,
                Email VARCHAR(100) UNIQUE NOT NULL,
                Password VARCHAR(255) NOT NULL,
                Profile_picture VARCHAR(255),
                Bio TEXT,
                Is_active INTEGER DEFAULT 1,
                Created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
                Updated_at TEXT DEFAULT CURRENT_TIMESTAMP
            );"""
        )
    }

    fun registerUser(username: String, email: String, password: String): Long {
        val db = this.writableDatabase
        val userId: Long

        // Verificar si el correo ya existe
        val cursor = db.rawQuery("SELECT User_id FROM Users WHERE Email = ?", arrayOf(email))

        if (cursor.count > 0) {
            cursor.close()
            userId = -1 // Devuelve -1 si el correo ya está registrado
        } else {
            // Si no existe, procede con el registro
            val contentValues = ContentValues().apply {
                put("Username", username)
                put("Email", email)
                put("Password", password)
                put("Is_active", 1)
            }
            userId = db.insert("Users", null, contentValues)
        }

        return userId
//        cursor.close() TODO: Descomentar cuando se termine el código.
//        db.close() TODO: Descomentar cuando se termine el código.
    }


    fun loginUser(email: String, password: String): Long {
        val db = this.readableDatabase
        var userId: Long = -1 // Valor por defecto si no se encuentra el usuario

        // Consulta a la base de datos
        val cursor = db.rawQuery(
            "SELECT User_id FROM Users WHERE Email = ? AND Password = ?",
            arrayOf(email, password)
        )

        // Verifica si la consulta devolvió algún resultado
        if (cursor.moveToFirst()) {
            userId = cursor.getLong(cursor.getColumnIndexOrThrow("User_id"))
        }

//        cursor.close() TODO: Descomentar cuando se termine el código.
//        db.close() TODO: Descomentar cuando se termine el código.
        return userId
    }

    fun updateUser(userId: Int, username: String? = null, email: String? = null, password: String? = null, bio: String? = null, profilePicture: String? = null) {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        // Solo se añade un valor si no es nulo
        username?.let { contentValues.put("Username", it) }
        email?.let { contentValues.put("Email", it) }
        password?.let { contentValues.put("Password", it) }
        bio?.let { contentValues.put("Bio", it) }
        profilePicture?.let { contentValues.put("Profile_picture", it) }

        // Actualiza la fila correspondiente al userId
        db.update("Users", contentValues, "User_id = ?", arrayOf(userId.toString()))
//        db.close() TODO: Descomentar cuando se termine el código.
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        // TODO: Código
    }
}