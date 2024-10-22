package com.example.chefhub.screens

import android.content.Context
import android.widget.Toast
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chefhub.data.DataUser
import com.example.chefhub.navigation.AppScreens
import com.example.chefhub.screens.components.ClickableText
import com.example.chefhub.screens.components.PasswordTextField
import com.example.chefhub.screens.components.SimpleButton
import com.example.chefhub.screens.components.SimpleTextField
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
            value = appUiState.newUser,
            onValueChange = { appViewModel.onRegisterChanged(it, "user") },
            label = "Usuario",
            required = true
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Campo de texto para ingresar el nuevo correo electrónico.
        SimpleTextField(
            value = appUiState.newEmail,
            onValueChange = { appViewModel.onRegisterChanged(it, "email") },
            label = "Correo electrónico",
            required = true
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Campo de texto para ingresar la nueva contraseña.
        PasswordTextField(
            value = appUiState.newPassword,
            onValueChange = { appViewModel.onRegisterChanged(it, "password") },
            label = "Contraseña",
            required = true
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Campo de texto para confirmar la nueva contraseña.
        PasswordTextField(
            value = appUiState.confirmNewPassword,
            onValueChange = { appViewModel.onRegisterChanged(it, "confirmPassword") },
            label = "Confirmar contraseña",
            required = true
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Botón para registrar la nueva cuenta.
        SimpleButton(
            texto = "Registrarse",
            onClick = { appViewModel.checkRegister(context) { registerSuccesful ->
                if (registerSuccesful) {
                    navController.navigate(AppScreens.MainScreen.route)
                }
            } },
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Texto clicable para iniciar sesión.
        ClickableText(mensaje = "¿Ya tienes una cuenta? ", enlace = "Iniciar sesión", ruta = AppScreens.LoginScreen.route, navController = navController)
    }
}