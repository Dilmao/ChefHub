package com.example.chefhub.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SimpleButton(
    texto: String,          // Texto que se motrara en el botón.
    onClick: () -> Unit,    // Acción que se ejecutará cuando el botón sea presionado.
) {
    Button(onClick = onClick) {
        Text(text = texto)
    }
}

@Composable
fun SettingButton( // TODO: Modificar diseño del botón
    title: String,          // Texto que se motrara en el botón.
    onClick: () -> Unit,    // Acción que se ejecutará cuando el botón sea presionado.
) {
    Button(onClick = onClick) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title)
            Text(text = ">")
        }
    }
}