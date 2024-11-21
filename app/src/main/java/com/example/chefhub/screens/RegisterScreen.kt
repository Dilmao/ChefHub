package com.example.chefhub.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chefhub.navigation.AppScreens
import com.example.chefhub.screens.components.ClickableText
import com.example.chefhub.screens.components.PasswordTextField
import com.example.chefhub.screens.components.SimpleButton
import com.example.chefhub.screens.components.SimpleTextField
import com.example.chefhub.screens.components.showMessage
import com.example.chefhub.ui.AppViewModel

@Composable
fun RegisterScreen(navController: NavHostController, appViewModel: AppViewModel) {
    // Estructura principal de la pantalla de registro.
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
            RegisterScreenBodyContent(navController, appViewModel)
        }
    }
}

@Composable
fun RegisterScreenBodyContent(navController: NavHostController, appViewModel: AppViewModel) {
    // Se obtiene el contexto y el estado de la UI.
    val appUiState by appViewModel.appUiState.collectAsState()
    val context = LocalContext.current
    var confirmPassword by rememberSaveable { mutableStateOf("") } // TODO: Borrar el estado al terminar

    // Estructura en columna para alinear los elementos.
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        // Campo de texto para ingresar el nuevo nombre de usuario.
        SimpleTextField(
            value = appUiState.user.userName,
            onValueChange = { appViewModel.onRegisterChanged(it, "userName") },
            label = "Usuario",
            required = true
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Campo de texto para ingresar el nuevo correo electrónico.
        SimpleTextField(
            value = appUiState.user.email,
            onValueChange = { appViewModel.onRegisterChanged(it, "email") },
            label = "Correo electrónico",
            required = true
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Campo de texto para ingresar la nueva contraseña.
        PasswordTextField(
            value = appUiState.user.password,
            onValueChange = { appViewModel.onRegisterChanged(it, "password") },
            label = "Contraseña",
            required = true
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Campo de texto para confirmar la nueva contraseña.
        PasswordTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = "Confirmar contraseña",
            required = true
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Botón para registrar la nueva cuenta.
        SimpleButton(
            texto = "Registrarse",
            onClick = {
                if (appUiState.user.password.equals(confirmPassword)) {
                    appViewModel.checkRegister(context) { registerSuccesful ->
                        if (registerSuccesful) {
                            navController.navigate(AppScreens.MainScreen.route)
                        }
                    }
                } else {
                    showMessage(context, "Ambas contraseñas deben ser iguales")
                }
            },
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