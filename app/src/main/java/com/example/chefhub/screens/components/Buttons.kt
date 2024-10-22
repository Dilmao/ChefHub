package com.example.chefhub.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF00796B),
            contentColor = Color.White
        ),
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title)
            Text(text = ">")
        }
    }
}