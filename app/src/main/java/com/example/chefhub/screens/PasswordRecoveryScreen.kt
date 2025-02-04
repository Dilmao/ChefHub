package com.example.chefhub.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chefhub.R
import com.example.chefhub.navigation.AppScreens
import com.example.chefhub.screens.components.ClickableText
import com.example.chefhub.screens.components.PasswordRecoveryAlert
import com.example.chefhub.screens.components.SimpleButton
import com.example.chefhub.screens.components.SimpleTextField
import com.example.chefhub.screens.components.showMessage
import com.example.chefhub.ui.AppViewModel

@Composable
fun PasswordRecoveryScreen(navController: NavController, appViewModel: AppViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Contenido principal de PasswordRecoveryScreen.
        PasswordRecoveryContent(navController, appViewModel)
    }
}

@Composable
private fun PasswordRecoveryContent(navController: NavController, appViewModel: AppViewModel) {
    // Se obtiene el contexto y el estado de la UI.
    val appUiState by appViewModel.appUiState.collectAsState()
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    // Diseño de la pantalla.
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        // Logo de ChefHub.
        Image(
            painter = painterResource(id = R.drawable.logo_no_bg),
            contentDescription = "Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(300.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))

        // Instrucciones para el usuario.
        Text(
            text = "Introduzca la dirección de correo electrónico asociada con su cuenta y le enviaremos un enlace para restablecer su contraseña.",
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Campo para el correo electrónico.
        SimpleTextField(
            value = appUiState.user.email,
            onValueChange = { appViewModel.onUserChanged(it, "email") },
            label = "Correo electrónico",
            required = true
        )
        Spacer(modifier = Modifier.height(20.dp))
        
        // Botón para enviar el enlace de recuperación.
        SimpleButton(
            texto = "Reestablecer contraseña",
            onClick = {
                // Se valida y procesa el restablecimiento de contraseña.
                appViewModel.recoverPassword { validation ->
                    when (validation) {
                        1 -> showMessage(context, "El campo 'correo electrónico' no puede estar vacío.")
                        2 -> showMessage(context, "No se ha encontrado un usuario con el correo introducido.")
                        3 -> showMessage(context, "Error inesperado. Por favor, contacte con soporte técnico.")
                        else -> showDialog = true
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Texto clicable para iniciar sesión.
        ClickableText(
            mensaje = "Volver al ",
            enlace = "Inicio de sesión",
            onClick = {
                appViewModel.resetUserValues()
                navController.navigate(AppScreens.LoginScreen.route)
            }
        )
    }

    // Alerta para confirmar que se envió el correo electrónico.
    if (showDialog) {
        PasswordRecoveryAlert(
            message = "Se ha enviado un correo electrónico a la dirección: \"${appUiState.user.email}\".\n Siga las instrucciones en el correo para cambiar la contraseña.",
            onDismissRequest = {
                showDialog = false
                appViewModel.resetUserValues()
                navController.navigate(AppScreens.LoginScreen.route)
            }
        )
    }
}