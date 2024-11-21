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
import com.example.chefhub.screens.components.loadCredentials
import com.example.chefhub.ui.AppViewModel

@Composable
fun LoginScreen(navController: NavHostController, appViewModel: AppViewModel) {
    // Estructura principal de la pantalla de login.
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
            LoginScreenBodyContent(navController, appViewModel)
        }
    }
}

@Composable
fun LoginScreenBodyContent(navController: NavHostController, appViewModel: AppViewModel) {
    // Se obtiene el contexto y el estado de la UI.
    val appUiState by appViewModel.appUiState.collectAsState()
    val context = LocalContext.current
    var loadedCredentials by rememberSaveable { mutableStateOf(false) }

    // Se intentan cargar las credenciales guardadas.
    if (!loadedCredentials) {
        val (savedEmail, savedPassword) = loadCredentials(context)
        if (savedEmail != null && savedPassword != null) {
            appViewModel.onLoginChanged(savedEmail, savedPassword)
            loadedCredentials = true
        }
    }

    // Estructura en columna para alinear los elementos.
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        // Campo de texto para ingresas el correo electrónico del usuario.
        SimpleTextField(
            value = appUiState.user.email,
            onValueChange = { appViewModel.onLoginChanged(it, appUiState.user.password) },
            label = "Correo electrónico",
            required = true
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Campo de texto para ingresas la contraseña del usuario.
        PasswordTextField(
            value = appUiState.user.password,
            onValueChange = { appViewModel.onLoginChanged(appUiState.user.email, it) },
            label = "Contraseña",
            required = true
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Botón para iniciar sesión.
        SimpleButton(
            texto = "Iniciar sesión",
            onClick = {
                appViewModel.checkLogin(context) { loginSuccessful ->
                if (loginSuccessful) {
                    appViewModel.resetUserValues()
                    navController.navigate(AppScreens.MainScreen.route)
                }
            }
                      },
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Textos clicables para registrarse o recuperar la contraseña.
        ClickableText(
            mensaje = "¿No tienes una cuenta? ",
            enlace = "Registrarse",
            onClick = {
                appViewModel.resetUserValues()
                navController.navigate(AppScreens.RegisterScreen.route)
            }
        )
        ClickableText(
            mensaje = "¿Has olvidado tu contraseña?  ",
            enlace = "Ayuda",
            onClick = {
                appViewModel.resetUserValues()
                navController.navigate(AppScreens.PasswordRecoveryScreen.route)
            }
        )
    }
}
