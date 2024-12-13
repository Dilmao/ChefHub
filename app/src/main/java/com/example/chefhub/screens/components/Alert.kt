package com.example.chefhub.screens.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ContentAlert(
    title: String = "Funcionalidad no disponible",
    message: String = "Esta funcionalidad está planificada para una futura versión. ¡Esté atento a las próximas actualizaciones!",
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        title = { Text(title, style = MaterialTheme.typography.titleMedium) },
        text = { Text(message, style = MaterialTheme.typography.bodyMedium) },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Aceptar", style = TextStyle(fontSize = 16.sp))
            }
        },
        tonalElevation = 4.dp
    )
}

@Composable
fun PasswordRecoveryAlert(
    title: String = "Reestablecer contraseña",
    message: String,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        title = { Text(title, style = MaterialTheme.typography.titleMedium) },
        text = { Text(message, style = MaterialTheme.typography.bodyMedium) },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Aceptar", style = TextStyle(fontSize = 16.sp))
            }
        },
        tonalElevation = 4.dp
    )
}
