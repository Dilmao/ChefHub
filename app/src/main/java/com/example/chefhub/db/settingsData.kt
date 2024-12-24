package com.example.chefhub.db

import androidx.navigation.NavController
import com.example.chefhub.navigation.AppScreens
import com.example.chefhub.screens.components.ContentAlert
import com.example.chefhub.ui.AppViewModel

// Data class que acepta una acción que utiliza AppViewModel.
data class SettingOption(
    val title: String,
    val onClick: () -> Unit,
    val showDialog: Boolean = false
)

// Opciones principales de configuración.
fun getMainSettingsOptions(
    appViewModel: AppViewModel,
    navController: NavController
): List<SettingOption> = listOf(
    SettingOption(
        title= "Opciones de cuenta",
        onClick = {
            appViewModel.onChangeSettingsScreen(getAccountOptions(appViewModel, navController))
        }
    ),
    SettingOption(
        title= "Notificaciones",
        onClick = {
            appViewModel.onChangeSettingsScreen(getNotificationOptions(appViewModel, navController))
        }
    ),
    SettingOption(
        title= "Privacidad",
        onClick = {
            appViewModel.onChangeSettingsScreen(getPrivacyOptions(appViewModel, navController))
        }
    ),
    SettingOption(
        title= "Comentarios y Soporte",
        onClick = {
            appViewModel.onChangeSettingsScreen(getCommentsOptions(appViewModel, navController))
        }
    ),
    SettingOption(
        title= "Accesibilidad",
        onClick = {
            appViewModel.onChangeSettingsScreen(getAccessibilityOptions(appViewModel, navController))
        }
    ),
)

// Opciones de cuenta.
private fun getAccountOptions(
    appViewModel: AppViewModel,
    navController: NavController
): List<SettingOption> = listOf(
    SettingOption(
        title= "Editar perfil",
        onClick = { navController.navigate(AppScreens.EditUserInfoScreen.route) }
    ),
    SettingOption(
        title= "Cambiar contraseña",
        onClick = { navController.navigate(AppScreens.PasswordRecoveryScreen.route) }
    ),
    SettingOption(
        title= "Cerrar sesión",
        onClick = {
            appViewModel.logOut()
            navController.navigate(AppScreens.LoginScreen.route)
        }
    ),
    SettingOption(
        title= "Borrar cuenta",
        onClick = {
            appViewModel.deleteAccount()
            navController.navigate(AppScreens.LoginScreen.route)
        }
    ),
)

// Opciones de notificaciones.
private fun getNotificationOptions(
    appViewModel: AppViewModel,
    navController: NavController,
): List<SettingOption> = listOf(
    SettingOption(
        title = "Recibir notificaciones",
        onClick = { /* TODO */ },
        showDialog = true
    )
)

// Opciones de privacidad.
private fun getPrivacyOptions(
    appViewModel: AppViewModel,
    navController: NavController,
): List<SettingOption> = listOf(
    SettingOption(
        title = "Cambiar privacidad",
        onClick = { /* TODO */ },
        showDialog = true
    )
)

// Opciones de comentarios y soporte.
private fun getCommentsOptions(
    appViewModel: AppViewModel,
    navController: NavController,
): List<SettingOption> = listOf(
    SettingOption(
        title = "Ver comentarios",
        onClick = { /* TODO */ },
        showDialog = true
    ),
    SettingOption(
        title = "Contacto con soporte",
        onClick = { /* TODO */ },
        showDialog = true
    )
)

// Opciones de accesibilidad.
private fun getAccessibilityOptions(
    appViewModel: AppViewModel,
    navController: NavController,
): List<SettingOption> = listOf(
    SettingOption(
        title = "Modo oscuro",
        onClick = { /* TODO */ },
        showDialog = true
    ),
    SettingOption(
        title = "Cambiar de idioma",
        onClick = { /* TODO */ },
        showDialog = true
    ),
    SettingOption(
        title = "Tamaño de fuente",
        onClick = { /* TODO */ },
        showDialog = true
    )
)
