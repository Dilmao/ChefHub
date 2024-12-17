package com.example.chefhub.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chefhub.navigation.AppScreens
import com.example.chefhub.scaffold.MyMainBottomBar
import com.example.chefhub.scaffold.MyMainTopBar
import com.example.chefhub.screens.components.RecipeCard
import com.example.chefhub.ui.AppViewModel

@Composable
fun MainScreen(navController: NavHostController, appViewModel: AppViewModel) {
    // COMENTARIO.
    Scaffold(
        topBar = { MyMainTopBar() },
        bottomBar = { MyMainBottomBar("Main", navController) }
    ) {paddingValues ->
        // COMENTARIO.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // COMENTARIO.
            MainScreenBodyContent(navController, appViewModel)
        }
    }
}

@Composable
fun MainScreenBodyContent(navController: NavHostController, appViewModel: AppViewModel) {
    // COMENTARIO.
    val appUiState by appViewModel.appUiState.collectAsState()
    val context = LocalContext.current
    var loaded by remember { mutableStateOf(false) }

    // TODO: La primera vez que se carga MainScreen, no se cargan las recetas.
    // COMENTARIO.
    if (!loaded) {
        appViewModel.loadMain()
        loaded = true
    }

    // COMENTARIO.
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxWidth().padding(10.dp)
    ) {
        items(appUiState.recipes.size) { index ->
            if (appUiState.recipes[index].userId != appUiState.user.userId) {
                RecipeCard(
                    recipe = appUiState.recipes[index],
                    onClick = {
                        appViewModel.onSelectRecipe(appUiState.recipes[index])
                        navController.navigate(AppScreens.RecipeScreen.route)
                    }
                )
            }
        }
    }
}
