package com.example.chefhub.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chefhub.navigation.AppScreens
import com.example.chefhub.scaffold.MyMainBottomBar
import com.example.chefhub.screens.components.CategoryCard
import com.example.chefhub.screens.components.ContentAlert
import com.example.chefhub.screens.components.InvisibleButton
import com.example.chefhub.screens.components.RecipeCard
import com.example.chefhub.screens.components.SearchField
import com.example.chefhub.screens.components.UserCard
import com.example.chefhub.ui.AppUiState
import com.example.chefhub.ui.AppViewModel

@Composable
fun SearchScreen(navController: NavController, appViewModel: AppViewModel) {
    // Estructura de la pantalla.
    Scaffold(
        bottomBar = { MyMainBottomBar("Search", navController) }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            // Contenido principal de SearchScreen.
            SearchContent(navController, appViewModel)
        }
    }
}

@Composable
private fun SearchContent(navController: NavController, appViewModel: AppViewModel) {
    // Se obtiene el contexto y el estado de la UI.
    val appUiState by appViewModel.appUiState.collectAsState()
    var selectedSearchType by rememberSaveable { mutableStateOf("recipes") }
    var showDialog by remember { mutableStateOf(false) }

    // Se realiza la búsqueda al cambiar el tipo o la query de búsqueda.
    LaunchedEffect(selectedSearchType) {
        appViewModel.search(selectedSearchType)
    }

    // Diseño de la pantalla.
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Barra de búsqueda.
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            SearchField(
                value = appUiState.search,
                onValueChange = { appViewModel.onSearchChanged(it) },
                onSearch= { appViewModel.search(selectedSearchType) }
            )
        }

        // Botones para cambiar el tipo de búsqueda.
        SearchTypeButtons(
            onTypeSelected = { type ->
                selectedSearchType = type
            }
        )

        // Se muestran los resultados según el tipo de búsqueda.
        SearchResults(
            selectedSearchType = selectedSearchType,
            appViewModel = appViewModel,
            appUiState = appUiState,
            navController = navController,
            onCategorySelected = { showDialog = true }
        )
    }

    // Diálogo emergente.
    if (showDialog) {
        ContentAlert(onDismissRequest = { showDialog = false })
    }
}

@Composable
private fun SearchTypeButtons(onTypeSelected: (String) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        InvisibleButton(
            texto = "Recetas",
            onClick = { onTypeSelected("recipes") }
        )
        InvisibleButton(
            texto = "Usaurios",
            onClick = { onTypeSelected("users") }
        )
        InvisibleButton(
            texto = "Categorias",
            onClick = { onTypeSelected("categories") }
        )
    }
}

@Composable
private fun SearchResults(
    selectedSearchType: String,
    appViewModel: AppViewModel,
    appUiState: AppUiState,
    navController: NavController,
    onCategorySelected: () -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(10.dp)
    ) {
        when (selectedSearchType) {
            "users" -> {
                items(appUiState.users.size) { index ->
                    val user = appUiState.users[index]
                    if (user.userId != appUiState.user.userId) {
                        UserCard(
                            user = user,
                            onClick = {
                                appViewModel.onSelectUser(user)
                                navController.navigate(AppScreens.ViewedUserScreen.route)
                            }
                        )
                    }
                }
            }
            "recipes" -> {
                items(appUiState.recipes.size) { index ->
                    val recipe = appUiState.recipes[index]
                    if (recipe.userId != appUiState.user.userId) {
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
            "categories" -> {
                items(appUiState.categories.size) { index ->
                    val category = appUiState.categories[index]
                    CategoryCard(
                        category = category,
                        onClick = onCategorySelected
                    )
                }
            }
        }
    }
}
