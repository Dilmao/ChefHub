package com.example.chefhub.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.navigation.NavController

@Composable
fun ClickableText(
    mensaje: String,                // Texto estático que precede al enlace.
    enlace: String,                 // Texto del enlace que se puede hacer clic.
    onClick: () -> Unit,            // COMENTARIO.
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Muestra el mensaje estático antes del enlace.
        Text(text = mensaje)

        // Muestra el texto del enlace que puede ser clicado con su estilo.
        ClickableText(
            text = AnnotatedString(enlace),
            onClick = { onClick() },
            style = TextStyle(
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            )
        )
    }
}