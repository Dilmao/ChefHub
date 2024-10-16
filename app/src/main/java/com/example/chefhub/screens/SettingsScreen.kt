package com.example.chefhub.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chefhub.navigation.AppScreens
import com.example.chefhub.screens.components.SettingButton
import com.example.chefhub.ui.AppViewModel

@Composable
fun SettingsScreen(navController: NavController, appViewModel: AppViewModel) {
    // COMENTARIO.
    Scaffold(
        topBar = { /* TODO */ },
    ) { paddingValues ->
        // COMENTARIO.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // COMENTARIO.
            SettingsScreenContent(navController, appViewModel)
        }
    }
}

@Composable
fun SettingsScreenContent(navController: NavController, appViewModel: AppViewModel) {
    // COMENTARIO.
    val appUiState by appViewModel.appUiState.collectAsState()

    // COMENTARIO.
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        SettingButton(
            title = "Opciones de cuenta",
            onClick = { navController.navigate(AppScreens.AddRecipeScreen.route) }
        )

        SettingButton(
            title = "Notificaciones",
            onClick = { navController.navigate(AppScreens.MainScreen.route) }
        )

        SettingButton(
            title = "Privacidad de la cuenta",
            onClick = { navController.navigate(AppScreens.MainScreen.route) }
        )

        SettingButton(
            title = "Comentarios",
            onClick = { navController.navigate(AppScreens.MainScreen.route) }
        )

        SettingButton(
            title = "Accesibilidad",
            onClick = { navController.navigate(AppScreens.MainScreen.route) }
        )
    }
}
