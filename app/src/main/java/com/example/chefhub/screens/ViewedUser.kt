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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chefhub.R
import com.example.chefhub.db.data.Recipes
import com.example.chefhub.navigation.AppScreens
import com.example.chefhub.scaffold.MyMainBottomBar
import com.example.chefhub.screens.components.ContentAlert
import com.example.chefhub.screens.components.FollowButton
import com.example.chefhub.screens.components.InvisibleButton
import com.example.chefhub.screens.components.RecipeCard
import com.example.chefhub.ui.AppUiState
import com.example.chefhub.ui.AppViewModel

@Composable
fun ViewedUserScreen(navController: NavController, appViewModel: AppViewModel) {
    // Estructura de la pantalla.
    Scaffold(
        bottomBar = { MyMainBottomBar("Search", navController) }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            // Contenido principal de ViewedUserScreen.
            ViewedUserContent(navController, appViewModel)
        }
    }
}

@Composable
fun ViewedUserContent(navController: NavController, appViewModel: AppViewModel) {
    // Se obtiene el contexto y el estado de la UI.
    val appUiState by appViewModel.appUiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var isFollowing by remember { mutableStateOf(false) }

    // Se comprueba si se sigue al usaurio o no.
    appUiState.following.forEach { user ->
        if (user.userId == appUiState.viewedUser.userId) {
            isFollowing = true
        }
    }

    // Diseño de la pantalla.
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Sección de información del usuario.
        UserInfoSection(
            appUiState = appUiState,
            onClick = { showDialog = true }
        )
        Spacer(Modifier.height(10.dp))

        // Biografia del usuario.
        UserBioSection(appUiState.viewedUser.userName, appUiState.viewedUser.bio)
        Spacer(Modifier.height(20.dp))

        // Botón para seguir al usuario.
        FollowSection(
            isFollowing = isFollowing,
            onClick = { appViewModel.onFollowUser(isFollowing) }
        )
        Spacer(Modifier.height(20.dp))

        // Barra de navegación de recetas.
        RecipeNavigationBar(
            onRecipesClick = { appViewModel.changeView("recipes", appUiState.viewedUser.userId) },
            onSavedClick = { appViewModel.changeView("saved", appUiState.viewedUser.userId) }
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

    if (showDialog) {
        ContentAlert(
            onDismissRequest = { showDialog = false }
        )
    }
}

@Composable
private fun UserInfoSection(
    appUiState: AppUiState,
    onClick: () -> Unit
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
            onClick = onClick
        )
        InvisibleButton(
            texto = "${appUiState.followers.size}\nSeguidores",
            onClick = onClick
        )
        InvisibleButton(
            texto = "${appUiState.following.size}\nSeguidos",
            onClick = onClick
        )
    }
}

@Composable
private fun UserBioSection(
    userName: String,
    bio: String?
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp)
    ) {
        Text(userName, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        bio?.let { Text(text = it) }
    }
}

@Composable
private fun FollowSection(
    isFollowing: Boolean,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        FollowButton(
            onClick = { onClick() },
            isFollowed = isFollowing
        )
    }
}

@Composable
private fun RecipeNavigationBar(
    onRecipesClick: () -> Unit,
    onSavedClick: () -> Unit
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
