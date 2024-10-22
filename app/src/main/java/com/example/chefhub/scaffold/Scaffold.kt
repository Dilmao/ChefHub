package com.example.chefhub.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chefhub.navigation.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAccountTopAppBar(
    titulo: String,
    navController: NavController
) {
    TopAppBar(
        title = { Text(text = titulo, color = Color.Black) }, // Título en negro
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFF5F5F5), // Fondo gris claro
            titleContentColor = Color.Black,
            actionIconContentColor = Color.Black
        ),
        actions = {
            IconButton(onClick = { navController.navigate(AppScreens.SettingsScreen.route) }) {
                Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings", tint = Color.Black)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyGeneralTopAppBar(
    titulo: String,
) {
    TopAppBar(
        title = { Text(
            text = titulo,
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Black // Título en negro
        ) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFF5F5F5), // Fondo gris claro
            titleContentColor = Color.Black,
            actionIconContentColor = Color.Black
        )
    )
}

@Composable
fun MyMainBottomBar(
    screen: String,
    navController: NavController
) {
    BottomAppBar(
        containerColor = Color(0xFFF5F5F5), // Fondo gris claro
        contentColor = Color.Black, // Color del contenido
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                PersonalizedIconButton(
                    icon = Icons.Filled.Home,
                    contentDescription = "Home",
                    condition = screen == "Main",
                    onClick = { navController.navigate(AppScreens.MainScreen.route) }
                )

                PersonalizedIconButton(
                    icon = Icons.Filled.Add,
                    contentDescription = "AddRecipe",
                    condition = screen == "AddRecipe",
                    onClick = { navController.navigate(AppScreens.AddRecipeScreen.route) }
                )

                PersonalizedIconButton(
                    icon = Icons.Filled.AccountCircle,
                    contentDescription = "Account",
                    condition = screen == "Account",
                    onClick = { navController.navigate(AppScreens.AccountScreen.route) }
                )
            }
        }
    )
}

@Composable
fun PersonalizedIconButton(
    icon: ImageVector,
    contentDescription: String,
    condition: Boolean,
    onClick: () -> Unit
) {
    if (condition) {
        IconButton(onClick = { onClick() }) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color(0xFF00796B), shape = CircleShape), // Fondo verde oscuro para el botón
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription,
                    tint = Color.White // Icono en blanco
                )
            }
        }
    } else {
        IconButton(onClick = { onClick() }) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = Color.Black // Icono en negro
            )
        }
    }
}
