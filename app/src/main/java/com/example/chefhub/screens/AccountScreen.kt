package com.example.chefhub.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.chefhub.scaffold.MyMainBottomBar
import com.example.chefhub.ui.AppViewModel

@Composable
fun AccountScreen(navController: NavController, appViewModel: AppViewModel) {
    // COMENTARIO.
    Scaffold(
        topBar = {},
        bottomBar = { MyMainBottomBar("Account", navController) }
    ) { paddingValues ->
        // COMENTARIO.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // COMENTARIO.
            AccountScreenContent(navController, appViewModel)
        }
    }
}

@Composable
fun AccountScreenContent(navController: NavController, appViewModel: AppViewModel) {
    // TODO: Diseño de la página.
}
