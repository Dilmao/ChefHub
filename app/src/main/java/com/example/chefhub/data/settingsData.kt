package com.example.chefhub.data

import com.example.chefhub.ui.AppViewModel

// Data class que acepta una acción que utiliza AppViewModel.
data class SettingOption(val title: String, val onClickAction: (AppViewModel) -> Unit)

// Definir las opciones de configuración con las acciones que llaman a AppViewModel.
val settingOptions = listOf(
    SettingOption(
        title = "Opciones de cuenta",
        onClickAction = { appViewModel -> appViewModel.onChangeSettingsScreen(accountOptions) }
    ),
    SettingOption(
        title = "Notificaciones",
        onClickAction = { appViewModel -> appViewModel.onChangeSettingsScreen(accountOptions) }
    ),
    SettingOption(
        title = "Privacidad de la cuenta",
        onClickAction = { appViewModel -> appViewModel.onChangeSettingsScreen(accountOptions) }
    ),
    SettingOption(
        title = "Comentarios",
        onClickAction = { appViewModel -> appViewModel.onChangeSettingsScreen(accountOptions) }
    ),
    SettingOption(
        title = "Accesibilidad",
        onClickAction = { appViewModel -> appViewModel.onChangeSettingsScreen(accountOptions) }
    )
)

private val accountOptions = listOf(
    SettingOption(
        title = "Cambiar nombre de usuario",
        onClickAction = { appViewModel -> appViewModel.onChangeSettingsScreen(notificationOptions) }
    ),
    SettingOption(
        title = "Cambiar correo electronico",
        onClickAction = { appViewModel -> appViewModel.onChangeSettingsScreen(notificationOptions) }
    ),
    SettingOption(
        title = "Cambiar contraseña",
        onClickAction = { appViewModel -> appViewModel.onChangeSettingsScreen(notificationOptions) }
    ),
    SettingOption(
        title = "Crear una nueva cuenta",
        onClickAction = { appViewModel -> appViewModel.onChangeSettingsScreen(notificationOptions) }
    ),
    SettingOption(
        title = "Cerrar sesión",
        onClickAction = { appViewModel -> appViewModel.onChangeSettingsScreen(notificationOptions) }
),
)

private val notificationOptions = listOf(
    SettingOption(
        title = "Recibir notifiaciones",
        onClickAction = { appViewModel -> appViewModel.onChangeSettingsScreen(privacyOptions) }
    )
)

private val privacyOptions = listOf(
    SettingOption(
        title = "Cambiar privacidad",
        onClickAction = { appViewModel -> appViewModel.onChangeSettingsScreen(commentsOptions) }
    )
)

private val commentsOptions = listOf(
    SettingOption(
        title = "Ver comentarios",
        onClickAction = { appViewModel -> appViewModel.onChangeSettingsScreen(accesibilityOptions) }
    )
)

private val accesibilityOptions = listOf(
    SettingOption(
        title = "Accesibilidad 1",
        onClickAction = { appViewModel -> appViewModel.restartBoolean() }
    ),
    SettingOption(
        title = "Accesibilidad 2",
        onClickAction = { appViewModel -> appViewModel.restartBoolean() }
    ),
    SettingOption(
        title = "Modo oscuro",
        onClickAction = { appViewModel -> appViewModel.restartBoolean() }
    ),
    SettingOption(
        title = "Cambiar de idioma",
        onClickAction = { appViewModel -> appViewModel.restartBoolean() }
    )
)
