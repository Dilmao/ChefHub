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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chefhub.R
import com.example.chefhub.navigation.AppScreens
import com.example.chefhub.scaffold.MyMainBottomBar
import com.example.chefhub.screens.components.FollowButton
import com.example.chefhub.screens.components.InvisibleButton
import com.example.chefhub.screens.components.RecipeCard
import com.example.chefhub.ui.AppViewModel

@Composable
fun ViewedUserScreen(navController: NavController, appViewModel: AppViewModel) {
    // COMENTARIO.
    val appUiState by appViewModel.appUiState.collectAsState()

    // COMENTARIO.
    Scaffold(
        topBar = {  },
        bottomBar = { MyMainBottomBar("Search", navController) }
    ) { paddingValues ->
        // COMENTARIO.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // COMENTARIO.
            ViewedUserScreenContent(navController, appViewModel)
        }
    }
}

@Composable
fun ViewedUserScreenContent(navController: NavController, appViewModel: AppViewModel) {
    // COMENTARIO.
    val appUiState by appViewModel.appUiState.collectAsState()
    val context = LocalContext.current
    var isFollowing by remember { mutableStateOf(false) }
    appUiState.following.forEach { user ->
        if (user.userId == appUiState.viewedUser.userId) {
            isFollowing = true
        }
    }

    // COMENTARIO.
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // COMENTARIO.
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            // COMENTARIO.
            Image(
                painter = painterResource(id = R.drawable.icono_usuario_estandar),
                contentDescription = "Usuario",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )

            // COMENTARIO.
            Text("0\nRecetas", textAlign = TextAlign.Center)

            // COMENTARIO.
            Text("0\nSeguidores", textAlign = TextAlign.Center)

            // COMENTARIO.
            Text("0\nSeguidos", textAlign = TextAlign.Center)
        }
        Spacer(modifier = Modifier.height(10.dp))

        // COMENTARIO.
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
        ) {
            Text(appUiState.viewedUser.userName)
        }
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            FollowButton(
                onClick = { appViewModel.onFollowUser(isFollowing) },
                isFollowed = isFollowing
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        // COMENTARIO.
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // COMENTARIO.
            InvisibleButton(
                texto = "Mis recetas",
                onClick = { appViewModel.onChangeView("recipes") }
            )

            // COMENTARIO.
            Text("|")

            // COMENTARIO.
            InvisibleButton(
                texto = "Guardadas",
                onClick = { appViewModel.onChangeView("saved") }
            )

            // COMENTARIO.
            Text("|")

            // COMENTARIO.
            InvisibleButton(
                texto = "¿ALGO?",
                onClick = { appViewModel.onChangeView("something") }
            )
        }

        // COMENTARIO.
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(appUiState.recipes.size) { index ->
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
