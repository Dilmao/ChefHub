package com.example.chefhub.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chefhub.R
import com.example.chefhub.db.data.Recipes
import com.example.chefhub.navigation.AppScreens
import com.example.chefhub.scaffold.MyAccountTopAppBar
import com.example.chefhub.scaffold.MyMainBottomBar
import com.example.chefhub.screens.components.ContentAlert
import com.example.chefhub.screens.components.InvisibleButton
import com.example.chefhub.screens.components.RecipeCard
import com.example.chefhub.ui.AppUiState
import com.example.chefhub.ui.AppViewModel

@Composable
fun AccountScreen(navController: NavController, appViewModel: AppViewModel) {
    val appUiState by appViewModel.appUiState.collectAsState()

    // Estructura de la pantalla.
    Scaffold(
        topBar = { MyAccountTopAppBar(appUiState.user.userName, navController, appViewModel) },
        bottomBar = { MyMainBottomBar("Account", navController) }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            // Contenido principal de AccountScreen.
            AccountContent(navController, appViewModel)
        }
    }
}

@Composable
private fun AccountContent(navController: NavController, appViewModel: AppViewModel) {
    // Se obtiene el contexto y el estado de la UI.
    val appUiState by appViewModel.appUiState.collectAsState()
    var loadedRecipes by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    // Se cargan los datos de AccountScreen.
    if (!loadedRecipes) {
        appViewModel.loadAccount()
        loadedRecipes = true
    }

    // Diseño de la pantalla.
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Sección de información del usuario.
        UserInfoSection(
            appUiState = appUiState,
            onButtonClick = { showDialog = true } // En un futuro se quitara esto y se enviara el appViewModel para hacer las funciones.
        )
        Spacer(modifier = Modifier.height(10.dp))

        // Biografía del usuario.
        UserBioSection(appUiState.user.bio)
        Spacer(modifier = Modifier.height(20.dp))

        // Barra de navegación de recetas.
        RecipeNavigationBar(
            onRecipesClick = { appViewModel.changeView("recipes") },
            onSavedClick = { appViewModel.changeView("saved") },
            onOtherClick = { showDialog = true }
        )

        // Lista de recetas del usuario.
        UserRecipeList(
            recipes = appUiState.recipes,
            onRecipeClick = {
                appViewModel.onSelectRecipe(it)
                navController.navigate(AppScreens.RecipeScreen.route)
            }
        )
    }

    // Diálogo emergente.
    if (showDialog) {
        ContentAlert(onDismissRequest = { showDialog = false })
    }
}

@Composable
private fun UserInfoSection(
    appUiState: AppUiState,
    onButtonClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        // Imagen de perfil de usuario.
        Image(
            painter = painterResource(id = R.drawable.icono_usuario_estandar),
            contentDescription = "Usuario",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
        )

        // Botones con estadísticas del usuario.
        InvisibleButton(
            texto = "${appUiState.recipes.size}\nRecetas",
            onClick = onButtonClick
        )
        InvisibleButton(
            texto = "${appUiState.followers.size}\nSeguidores",
            onClick = onButtonClick
        )
        InvisibleButton(
            texto = "${appUiState.following.size}\nSeguidos",
            onClick = onButtonClick
        )
    }
}

@Composable
private fun UserBioSection(bio: String?) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp)
    ) {
        bio?.let { Text(text = it) }
    }
}

@Composable
private fun RecipeNavigationBar(
    onRecipesClick: () -> Unit,
    onSavedClick: () -> Unit,
    onOtherClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        InvisibleButton(
            texto = "Mis recetas",
            onClick = onRecipesClick
        )
        Text("|")
        InvisibleButton(
            texto = "Guardadas",
            onClick = onSavedClick
        )
        Text("|")
        InvisibleButton(
            texto = "¿ALGO?",
            onClick = onOtherClick
        )
    }
}

@Composable
private fun UserRecipeList(
    recipes: List<Recipes>,
    onRecipeClick: (Recipes) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(recipes.size) { index ->
            RecipeCard(
                recipe = recipes[index],
                onClick = { onRecipeClick(recipes[index]) }
            )
        }
    }
}
