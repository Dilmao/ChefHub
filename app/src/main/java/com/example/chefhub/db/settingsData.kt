package com.example.chefhub.db

import androidx.navigation.NavHostController
import com.example.chefhub.navigation.AppScreens
import com.example.chefhub.ui.AppViewModel

// Data class que acepta una acción que utiliza AppViewModel.x
data class SettingOption(
    val title: String,
    val onClickAction: (AppViewModel, () -> Unit, NavHostController) -> Unit
)

// Definir las opciones de configuración con las acciones que llaman a AppViewModel.
val settingOptions = listOf(
    SettingOption(
        title = "Opciones de cuenta",
        onClickAction = { appViewModel, _, navController ->
            appViewModel.onChangeSettingsScreen(accountOptions)
            navController.navigate(AppScreens.SettingsScreen.route)
        }
    ),
    SettingOption(
        title = "Notificaciones",
        onClickAction = { appViewModel, _, navController ->
            appViewModel.onChangeSettingsScreen(notificationOptions)
            navController.navigate(AppScreens.SettingsScreen.route)
        }
    ),
    SettingOption(
        title = "Privacidad",
        onClickAction = { appViewModel, _, navController ->
            appViewModel.onChangeSettingsScreen(privacyOptions)
            navController.navigate(AppScreens.SettingsScreen.route)
        }
    ),
    SettingOption(
        title = "Comentarios y Soporte",
        onClickAction = { appViewModel, _, navController ->
            appViewModel.onChangeSettingsScreen(commentsOptions)
            navController.navigate(AppScreens.SettingsScreen.route)
        }
    ),
    SettingOption(
        title = "Accesibilidad",
        onClickAction = { appViewModel, _, navController ->
            appViewModel.onChangeSettingsScreen(accesibilityOptions)
            navController.navigate(AppScreens.SettingsScreen.route)
        }
    ),
)

private val accountOptions = listOf(
    SettingOption(
        title = "Cambiar nombre de usuario",
        onClickAction = { _, showNotImplementedDialog, _ -> showNotImplementedDialog()}
    ),
    SettingOption(
        title = "Cambiar correo electronico",
        onClickAction = { _, showNotImplementedDialog, _ -> showNotImplementedDialog()}
    ),
    SettingOption(
        title = "Cambiar contraseña",
        onClickAction = { _, showNotImplementedDialog, _ -> showNotImplementedDialog()}
    ),
    SettingOption(
        title = "Cambiar biografía",
        onClickAction = { _, showNotImplementedDialog, _ -> showNotImplementedDialog()}
    ),
    SettingOption(
        title = "Cambiar foto de perfil",
        onClickAction = { _, showNotImplementedDialog, _ -> showNotImplementedDialog()}
    ),
    SettingOption(
        title = "Cerrar sesión",
        onClickAction = { appViewModel, _, navController ->
            appViewModel.logOut()
            navController.navigate(AppScreens.LoginScreen.route)
        }
    ),
    SettingOption(
        title = "Borrar cuenta",
        onClickAction = { appViewModel, _, navController ->
            appViewModel.deleteAccount()
            navController.navigate(AppScreens.LoginScreen.route)
        }
    ),
)

private val notificationOptions = listOf(
    SettingOption(
        title = "Recibir notifiaciones",
        onClickAction = { _, showNotImplementedDialog, _ -> showNotImplementedDialog() }
    ),
)

private val privacyOptions = listOf(
    SettingOption(
        title = "Cambiar privacidad",
        onClickAction = { _, showNotImplementedDialog, _ -> showNotImplementedDialog()}
    ),
)

private val commentsOptions = listOf(
    SettingOption(
        title = "Ver comentarios",
        onClickAction = { _, showNotImplementedDialog, _ -> showNotImplementedDialog()}
    ),
    SettingOption(
        title = "Contacto con soporte",
        onClickAction = { _, showNotImplementedDialog, _ -> showNotImplementedDialog()}
    ),
)

private val accesibilityOptions = listOf(
    SettingOption(
        title = "Modo oscuro",
        onClickAction = { _, showNotImplementedDialog, _ -> showNotImplementedDialog()}
    ),
    SettingOption(
        title = "Cambiar de idioma",
        onClickAction = { _, showNotImplementedDialog, _ -> showNotImplementedDialog()}
    ),

    SettingOption(
        title = "Tamaño de fuente",
        onClickAction = { _, showNotImplementedDialog, _ -> showNotImplementedDialog()}
    ),
)
