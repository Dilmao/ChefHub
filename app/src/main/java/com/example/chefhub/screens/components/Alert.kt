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

@Composable
fun DeleteConfirmationAlert(
    itemType: String,
    confirm: (Boolean) -> Unit
) {
    var showDialog = true
    val message = when(itemType) {
        "user" -> "¿Está seguro de que desea eliminar este usuario? Esta acción no se puede deshacer."
        "recipe" -> "¿Está seguro de que desea eliminar esta receta? Esta acción no se puede deshacer."
        else -> ""
    }

    if (showDialog) {
        AlertDialog(
            title = { Text("Confirmar eliminación", style = MaterialTheme.typography.titleMedium) },
            text = { Text(message, style = MaterialTheme.typography.bodyMedium) },
            onDismissRequest = { confirm(false); showDialog = false },
            confirmButton = {
                TextButton(onClick = { confirm(true); showDialog = false }) {
                    Text("Aceptar", style = TextStyle(fontSize = 16.sp))
                }
            },
            dismissButton = {
                TextButton(onClick = { confirm(false); showDialog = false }) {
                    Text("Cancelar", style = TextStyle(fontSize = 16.sp))
                }
            },
            tonalElevation = 4.dp
        )
    }
}
