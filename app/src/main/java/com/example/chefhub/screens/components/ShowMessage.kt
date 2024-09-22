package com.example.chefhub.screens.components

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable

fun showMessage (
    context: Context,   // Contexto necesario para crear el Toast.
    mensaje: String     // Mensaje que se mostrará en el Toast.
) {
    // Crea y muestra un Toast con el mensaje proporcionado.
    Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
}