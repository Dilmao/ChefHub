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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chefhub.navigation.AppScreens
import com.example.chefhub.ui.AppViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAccountTopAppBar(// TODO: Cambiar los colores, mejorar los comentarios.
    titulo: String,                 // COMENTARIO.
    appViewModel: AppViewModel      // COMENTARIO.
) {
    // COMENTARIO.
    val scope = rememberCoroutineScope()

    // COMENTARIO.
    TopAppBar(
        title = { Text(text = titulo) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary
            ),
        actions = {
            IconButton(onClick = {
                scope.launch {
                    appViewModel.onChangeDrawerState()
                }
            }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
            }
        }
    )
}

@Composable
fun MyMainBottomBar(// TODO: Cambiar los colores, mejorar los comentarios.
    screen: String,                 // Página desde la que se hace la llamada.
    navController: NavController    // COMENTARIO.
    ) {
    // COMENTARIO.
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
        actions = {
            // Estructura en fila para alinear los elementos.
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Icono personalizado para navegar a 'MainScreen'.
                PersonalizedIconButton(
                    icon = Icons.Filled.Home,
                    contentDescription = "Home",
                    condition = screen.equals("Main"),
                    onClick = { navController.navigate(AppScreens.MainScreen.route) }
                )

                // Icono personalizado para navegar a 'AddScreen'.
                PersonalizedIconButton(
                    icon = Icons.Filled.Add,
                    contentDescription = "AddRecipe",
                    condition = screen.equals("AddRecipe"),
                    onClick = { navController.navigate(AppScreens.AddRecipeScreen.route) }
                )

                // Icono personalizado para navegar a 'AccountScreen'.
                PersonalizedIconButton(
                    icon = Icons.Filled.AccountCircle,
                    contentDescription = "Account",
                    condition = screen.equals("Account"),
                    onClick = { navController.navigate(AppScreens.AccountScreen.route) }
                )
            }
        }
    )
}

@Composable
fun PersonalizedIconButton(// TODO: Mejorar los comentarios.
    icon: ImageVector,          // Icono que se mostrara.
    contentDescription: String, // Descripción del icono para accesibilidad.
    condition: Boolean,         // Condición para modificar la visibilidad del icono.
    onClick: () -> Unit         // Acción que se ejecutara cuando el icono sea presionado.
) {
    // Se comprueba si se cumple la condición.
    if (condition) {
        // En caso de cumplirse, el botón no hara nada y se modifica su apariencia.
        IconButton(
            onClick = { onClick() },
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.Black, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription,
                    tint = Color.White
                )
            }
        }
    } else {
        // En caso de no cumplirse, el botón efectua su función y no se modifica su apariencia.
        IconButton(
            onClick = { onClick() }
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = Color.Black
            )
        }
    }
}