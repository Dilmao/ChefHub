package com.example.chefhub.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chefhub.navigation.AppScreens
import com.example.chefhub.scaffold.MyMainBottomBar
import com.example.chefhub.scaffold.MyMainTopBar
import com.example.chefhub.screens.components.RecipeCard
import com.example.chefhub.ui.AppViewModel

@Composable
fun MainScreen(navController: NavController, appViewModel: AppViewModel) {
    // Estructura de la pantalla.
    Scaffold(
        topBar = { MyMainTopBar() },
        bottomBar = { MyMainBottomBar("Main", navController) }
    ) {paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            // Contenido principal de MainScreen.
            MainContent(navController, appViewModel)
        }
    }
}

@Composable
private fun MainContent(navController: NavController, appViewModel: AppViewModel) {
    // Se obtiene el contexto y el estado de la UI.
    val appUiState by appViewModel.appUiState.collectAsState()
    var loaded by remember { mutableStateOf(false) }

    // Se cargan los datos de MainScreen.
    if (!loaded) {
        appViewModel.loadMain()
        loaded = true
    }

    // Diseño de la pantalla.
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(10.dp)
    ) {
        // Se muestran las recetas
        items(appUiState.recipes.size) { index ->
            val recipe = appUiState.recipes[index]

            RecipeCard(
                recipe = recipe,
                onClick = {
                    appViewModel.onSelectRecipe(recipe)
                    navController.navigate(AppScreens.RecipeScreen.route)
                }
            )
        }
    }
}
