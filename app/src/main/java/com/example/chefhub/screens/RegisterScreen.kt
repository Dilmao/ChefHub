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
import com.example.chefhub.screens.components.PasswordTextField
import com.example.chefhub.screens.components.SimpleButton
import com.example.chefhub.screens.components.SimpleTextField
import com.example.chefhub.screens.components.saveCredentials
import com.example.chefhub.screens.components.showMessage
import com.example.chefhub.ui.AppViewModel

@Composable
fun RegisterScreen(navController: NavController, appViewModel: AppViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Contenido principal de RegisterScreen.
        RegisterContent(navController, appViewModel)
    }
}

@Composable
private fun RegisterContent(navController: NavController, appViewModel: AppViewModel) {
    // Se obtiene el contexto y el estado de la UI.
    val appUiState by appViewModel.appUiState.collectAsState()
    val context = LocalContext.current
    var confirmPassword by remember { mutableStateOf("") }

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

        // Campo para el nombre de usaurio.
        SimpleTextField(
            value = appUiState.user.userName,
            onValueChange = { appViewModel.onUserChanged(it, "userName") },
            label = "Usuario",
            required = true
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

        // Campo para la contraseña.
        PasswordTextField(
            value = appUiState.user.password,
            onValueChange = { appViewModel.onUserChanged(it, "password") },
            label = "Contraseña",
            required = true
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Campo para confirmar la contraseña.
        PasswordTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = "Confirmar contraseña",
            required = true
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Botón para registrarse.
        SimpleButton(
            texto = "Registrarse",
            onClick = {
                // Se valida y procesa el registro.
                appViewModel.checkRegister(confirmPassword) { validation ->
                    when (validation) {
                        1 -> showMessage(context, "Uno o más campos están vacíos.")
                        2 -> showMessage(context, "El correo debe contener un '@'.")
                        3 -> showMessage(context, "La contraseña debe contener entre 10 y 30 caractere.")
                        4 -> showMessage(context, "Ambas contraseñas deben coindicir.")
                        5 -> showMessage(context, "El correo electrónico ya está registrado.")
                        6 -> showMessage(context, "El nombre introducido ya está en uso.")
                        7 -> showMessage(context, "Error inesperado. Por favor, contacte con soporte técnico.")
                        else -> {
                            saveCredentials(context, appUiState.user.email, appUiState.user.password)
                            showMessage(context, "Nuevo usuario creado con éxito.")
                            navController.navigate(AppScreens.MainScreen.route)
                        }
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Texto clicable para iniciar sesión.
        ClickableText(
            mensaje = "¿Ya tienes una cuenta? ",
            enlace = "Iniciar sesión",
            onClick = {
                appViewModel.resetUserValues()
                navController.navigate(AppScreens.LoginScreen.route)
            }
        )
    }
}