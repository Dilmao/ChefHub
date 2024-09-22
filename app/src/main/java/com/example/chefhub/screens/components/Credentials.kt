package com.example.chefhub.screens.components

import android.content.Context
import androidx.core.content.edit

/** FUNCIONES PRUEBA: GUARDAR DATOS DE INICIO DE SESION **/
// TODO: Pensar en usar tokens de identifiacion para mayor seguridad y comodidad
fun saveCredentials(context: Context, email: String, password: String) {
    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    sharedPreferences.edit {
        putString("email", email)
        putString("password", password)
        apply()
    }
}

fun loadCredentials(context: Context): Pair<String?, String?> {
    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val email = sharedPreferences.getString("email", null)
    val password = sharedPreferences.getString("password", null)
    return Pair(email, password)
}

fun clearCredentials(context: Context) {
    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    sharedPreferences.edit {
        remove("email")
        remove("password")
        apply()
    }
}