package com.example.chefhub.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chefhub.R
import com.example.chefhub.navigation.AppScreens
import com.example.chefhub.screens.components.ClickableText
import com.example.chefhub.screens.components.ContentAlert
import com.example.chefhub.screens.components.SimpleButton
import com.example.chefhub.screens.components.SimpleTextField
import com.example.chefhub.ui.AppViewModel

@Composable
fun EditUserInfoScreen(navController: NavController, appViewModel: AppViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Contenido principal de EditUserInfoScreen.
        EditUserInfoContent(navController, appViewModel)
    }
}

@Composable
fun EditUserInfoContent(navController: NavController, appViewModel: AppViewModel) {
    // Se obtiene el contexto y el estado de la UI.
    val appUiState by appViewModel.appUiState.collectAsState()
    var showAlert by remember { mutableStateOf(false) }
    var user by remember { mutableStateOf(appUiState.user) }

    // Diseño de la pantalla.
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(20.dp, top = 80.dp)
    ) {
        // Foto de perfil.
        Image(
            painter = painterResource(id = R.drawable.icono_usuario_estandar),
            contentDescription = "Foto de perfil",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.height(10.dp))

        // Texto para cambiar la foto de perfil.
        ClickableText(
            mensaje = "",
            enlace = "Cambiar foto de perfil",
            onClick = { showAlert = true }
        )
        Spacer(Modifier.height(20.dp))

        // Campo para el nombre de usuario.
        SimpleTextField(
            value = user.userName,
            onValueChange = { user = user.copy(userName = it) },
            label = "Nombre de usuario",
            required = true
        )
        Spacer(Modifier.height(20.dp))

        // Campo para la biografía.
        SimpleTextField(
            value = user.bio ?: "",
            onValueChange = { user = user.copy(bio = it) },
            label = "Biografía"
        )
        Spacer(Modifier.height(20.dp))

        // Botón para guardar los cambios.
        SimpleButton(
            texto = "Guardar",
            onClick = {
                appViewModel.onUserChanged(user, "all")
                navController.navigate(AppScreens.AccountScreen.route)
            }
        )
    }

    if (showAlert) {
        ContentAlert(onDismissRequest = { showAlert = false })
    }
}
