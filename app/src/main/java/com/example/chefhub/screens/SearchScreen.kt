package com.example.chefhub.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chefhub.R
import com.example.chefhub.navigation.AppScreens
import com.example.chefhub.scaffold.MyMainBottomBar
import com.example.chefhub.screens.components.ContentAlert
import com.example.chefhub.screens.components.InvisibleButton
import com.example.chefhub.screens.components.RecipeCard
import com.example.chefhub.screens.components.SearchField
import com.example.chefhub.screens.components.UserCard
import com.example.chefhub.ui.AppViewModel

@Composable
fun SearchScreen(navController: NavController, appViewModel: AppViewModel) {
    // COMENTARIO.
    Scaffold(
        bottomBar = { MyMainBottomBar("Search", navController) }
    ) { paddingValues ->
        // COMENTARIO.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // COMENTARIO.
            SearchScreenContent(navController, appViewModel)
        }
    }
}

@Composable
fun SearchScreenContent(navController: NavController, appViewModel: AppViewModel) {
    val appUiState by appViewModel.appUiState.collectAsState()
    val context = LocalContext.current
    var type by rememberSaveable { mutableStateOf("recipes") }
    var loaded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    // COMENTARIO.
    if (!loaded) {
        appViewModel.onSearch(type)
        loaded = true
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(10.dp)
        ) {
            SearchField(
                value = appUiState.search,
                onValueChange = { appViewModel.onSearchChanged(it) },
                onSearch= { showDialog = true }
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            InvisibleButton(
                texto = "Recetas",
                onClick = {
                    type = "recipes"
                    appViewModel.onSearch(type)
                }
            )

            InvisibleButton(
                texto = "Usuarios",
                onClick = {
                    type = "users"
                    appViewModel.onSearch(type)
                }
            )

            InvisibleButton(
                texto = "¿ALGO?",
                onClick = { showDialog = true }
            )
        }

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxWidth().padding(10.dp)
        ) {
            if (type.equals("users")) {
                items(appUiState.users.size) { index ->
                    if (appUiState.users[index].userId != appUiState.user.userId) {
                        UserCard(
                            user = appUiState.users[index],
                            onClick = {
                                appViewModel.onSelectUser(appUiState.users[index])
                                navController.navigate(AppScreens.ViewedUserScreen.route)
                            }
                        )
                    }
                }
            } else if (type.equals("recipes")) {
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
            } else if (type.equals("other")) {
                // TODO
            }
        }
    }

    if (showDialog) {
        ContentAlert(
            onDismissRequest = { showDialog = false }
        )
    }
}
