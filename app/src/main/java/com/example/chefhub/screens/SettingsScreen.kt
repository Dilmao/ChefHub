package com.example.chefhub.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chefhub.scaffold.MyGeneralTopAppBar
import com.example.chefhub.screens.components.ContentAlert
import com.example.chefhub.screens.components.SettingButton
import com.example.chefhub.ui.AppViewModel

@Composable
fun SettingsScreen(navController: NavHostController, appViewModel: AppViewModel) {
    Scaffold(
        topBar = { MyGeneralTopAppBar("Configuración") },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5)) // Fondo gris claro
        ) {
            SettingsScreenContent(navController, appViewModel)
        }
    }
}

@Composable
fun SettingsScreenContent(navController: NavHostController, appViewModel: AppViewModel) {
    // COMENTARIO.
    val appUiState by appViewModel.appUiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        // Iterar sobre las opciones de configuración para crear los botones.
        appUiState.settingsOptions.forEach { option ->
            SettingButton(
                title = option.title,
                onClick = {
                    option.onClickAction(appViewModel, { showDialog = true }, navController)
                }
            )
        }
    }

    if (showDialog) {
        ContentAlert(
            onDismissRequest = { showDialog = false }
        )
    }
}
