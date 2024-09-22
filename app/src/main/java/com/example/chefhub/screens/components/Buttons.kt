package com.example.chefhub.screens.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SimpleButton(
    texto: String,          // Texto que se motrara en el botón.
    onClick: () -> Unit,    // Acción que se ejecutará cuando el botón sea presionado.
) {
    Button(onClick = onClick) {
        Text(text = texto)
    }
}