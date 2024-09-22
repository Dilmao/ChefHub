package com.example.chefhub.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chefhub.navigation.AppScreens
import com.example.chefhub.screens.components.ClickableText
import com.example.chefhub.screens.components.SimpleButton
import com.example.chefhub.screens.components.SimpleTextField
import com.example.chefhub.ui.AppViewModel

@Composable
fun PasswordRecoveryScreen(navController: NavHostController, appViewModel: AppViewModel) {
    // Pantalla principal para el restablecimiento de contraseña.
    Scaffold(
        topBar = {}
    ) {paddingValues ->
        // Caja que ocupa el tamaño disponible aplicando un padding.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Contenido del cuerpo de la pantalla de inicio de sesión.
            PasswordRecoveryScreenBodyContent(navController, appViewModel)
        }
    }
}

@Composable
fun PasswordRecoveryScreenBodyContent(navController: NavHostController, appViewModel: AppViewModel) {
    // Se obtiene el estado actual de la UI desde el ViewModel.
    val appUiState by appViewModel.appUiState.collectAsState()

    // Estructura en columna para alinear los elementos.
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        // Texto que proporciona instrucciones sobre cómo restablecer la contraseña.
        // TODO: Mejorar la presentación de las instrucciones.
        Text(text = "Introduzca la dirección de correo electrónico asociada con su cuenta y le enviaremos un enlace para restablecer su contraseña.")
        Spacer(modifier = Modifier.height(20.dp))

        // Campo de texto para ingresar el correo electrónico.
        SimpleTextField(
            value = appUiState.recoveryEmail,
            onValueChange = { appViewModel.onRecoveryChanged(it) },
            label = "Correo electrónico",
            required = true
        )
        Spacer(modifier = Modifier.height(20.dp))
        
        // Botón para el restablecimiento de contraseña.
        SimpleButton(
            texto = "Reestablecer contraseña",
            onClick = { appViewModel.recoverPassword(appUiState.recoveryEmail) }
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Texto clicable para volver volver al inicio de sesión.
        ClickableText(mensaje = "Volver al ", enlace = "Inicio de sesión", ruta = AppScreens.LoginScreen.route, navController = navController)
    }

    // De ser necesario, se mostrara un mensaje de alerta.
    if (appUiState.showMessage) {
        AlertDialog(
            // Título del diálogo de restablecimiento de contraseña.
            title = { Text(text = "Reestablecer contraseña") },
            // Texto del mensaje que será mostrado en el diálogo.
            text = { Text(text = appUiState.messageText) },
            onDismissRequest = { /*TODO*/ },
            confirmButton = {
                // Botón de confirmación que reinicia el estado de la UI cuando se presiona "Aceptar".
                TextButton(
                    onClick = {
                        appViewModel.restartBoolean()
                    }
                ) {
                    Text("Aceptar")
                }
            },
        )
    }
}