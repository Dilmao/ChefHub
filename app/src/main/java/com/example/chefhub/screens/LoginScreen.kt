package com.example.chefhub.screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
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
import com.example.chefhub.screens.components.loadCredentials
import com.example.chefhub.screens.components.saveCredentials
import com.example.chefhub.screens.components.showMessage
import com.example.chefhub.ui.AppViewModel
import kotlin.system.exitProcess

@Composable
fun LoginScreen(navController: NavController, appViewModel: AppViewModel) {
    val context = LocalContext.current

    // Solicitud de permisos al inicio.
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.entries.forEach { entry ->
            if (!entry.value) {
                showMessage(context, "Permiso ${entry.key} denegado. Algunas funcionalidades pueden no estar disponibles.")
            }
        }
    }

    // Estado para controlar la solicitud de permisos.
    var permissionsRequested by remember { mutableStateOf(false) }

    // Lista de permisos según la versión del sistema.
    val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES
        )
    } else {
        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    // Se lanza una solicitud de permisos al montar la composición.
    LaunchedEffect(Unit) {
        if (!permissionsRequested) {
            permissionLauncher.launch(permissions)
            permissionsRequested = true
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Contenido principal de LoginScreen.
        LoginContent(navController, appViewModel)
    }
}

@Composable
private fun LoginContent(navController: NavController, appViewModel: AppViewModel) {
    // Se obtiene el contexto y el estado de la UI.
    val appUiState by appViewModel.appUiState.collectAsState()
    val context = LocalContext.current
    var loadedCredentials by remember { mutableStateOf(false) }

    // Se cargan las credenciales guardadas.
    if (!loadedCredentials) {
        val (savedEmail, savedPassword) = loadCredentials(context)
        if (savedEmail != null && savedPassword != null) {
            appViewModel.onUserChanged(savedEmail, "email")
            appViewModel.onUserChanged(savedPassword, "password")
        }
        loadedCredentials = true
    }

    // Diseño de la pantalla.
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().padding(20.dp)
    ) {
        // Logo de ChefHub.
        Image(
            painter = painterResource(id = R.drawable.logo_no_bg),
            contentDescription = "Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(300.dp)
        )
        Spacer(Modifier.height(40.dp))

        // Campo para el correo electrónico.
        SimpleTextField(
            value = appUiState.user.email,
            onValueChange = { appViewModel.onUserChanged(it, "email") },
            label = "Correo electrónico",
            required = true
        )
        Spacer(Modifier.height(20.dp))

        // Campo para la contraseña.
        PasswordTextField(
            value = appUiState.user.password,
            onValueChange = { appViewModel.onUserChanged(it, "password") },
            label = "Contraseña",
            required = true
        )
        Spacer(Modifier.height(20.dp))

        // Botón para iniciar sesión.
        SimpleButton(
            texto = "Iniciar sesión",
            onClick = {
                // Se valida y procesa el inicio de sesión.
                appViewModel.checkLogin { validation ->
                    when (validation) {
                        1 -> showMessage(context, "Uno o más campos están vacíos.")
                        2 -> showMessage(context, "Correo no encontrado en la base de datos.")
                        3 -> if (appUiState.tries != 0) showMessage(context, "Contraseña incorrecta. Intentos restantes: ${appUiState.tries}.")
                        4 -> showMessage(context, "Error inesperado. Por favor, contacte con soporte técnico.")
                        else -> {
                            saveCredentials(context, appUiState.user.email, appUiState.user.password)
                            showMessage(context, "Inicio de sesión exitoso.")
                            navController.navigate(AppScreens.MainScreen.route)
                        }
                    }

                    // Se cierra la aplicación si no quedan intentos.
                    if (appUiState.tries == 0) {
                        showMessage(context, "Se agotaron los intentos. La aplicación se cerrará.")
                        exitProcess(0)
                    }
                }
            }
        )
        Spacer(Modifier.height(20.dp))

        // Texto clicable para registrarse.
        ClickableText(
            mensaje = "¿No tienes una cuenta? ",
            enlace = "Registrarse",
            onClick = {
                appViewModel.resetUserValues()
                navController.navigate(AppScreens.RegisterScreen.route)
            }
        )

        // Texto clicable para recuperar la contraseña.
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
