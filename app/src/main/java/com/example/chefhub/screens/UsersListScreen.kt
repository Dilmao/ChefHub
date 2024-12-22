package com.example.chefhub.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.chefhub.ui.AppViewModel

@Composable
fun UsersListScreen(navController: NavController, appViewModel: AppViewModel) {
    // COMENTARIO.
    Box(modifier = Modifier.fillMaxSize()) {
        AccountScreenContent(navController, appViewModel)
    }
}