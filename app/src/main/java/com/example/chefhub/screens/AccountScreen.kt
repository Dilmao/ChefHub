package com.example.chefhub.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chefhub.R
import com.example.chefhub.scaffold.MyMainBottomBar
import com.example.chefhub.ui.AppViewModel

@Composable
fun AccountScreen(navController: NavController, appViewModel: AppViewModel) {
    // COMENTARIO.
    Scaffold(
        topBar = {},
        bottomBar = { MyMainBottomBar("Account", navController) }
    ) { paddingValues ->
        // COMENTARIO.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // COMENTARIO.
            AccountScreenContent(navController, appViewModel)
        }
    }
}

@Composable
fun AccountScreenContent(navController: NavController, appViewModel: AppViewModel) {
    // COMENTARIO.
    val appUiState by appViewModel.appUiState.collectAsState()
    val context = LocalContext.current

    // TODO: Me he venido arriba con las columnas y filas, arreglalo por dios.
    // COMENTARIO.
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // COMENTARIO.
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(10.dp)
        ) {
            // COMENTARIO.
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                // COMENTARIO.
                Image(
                    painter = painterResource(id = R.drawable.icono_usuario_estandar),
                    contentDescription = "Usuario",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(100.dp).clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(10.dp))

                // COMENTARIO.
                Text("[placeholder]")
            }
            Spacer(modifier = Modifier.width(20.dp))

            // COMENTARIO.
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // COMENTARIO.
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // COMENTARIO.
                    Text("0\nRecetas", textAlign = TextAlign.Center)

                    // COMENTARIO.
                    Text("0\nSeguidores", textAlign = TextAlign.Center)

                    // COMENTARIO.
                    Text("0\nSeguidos", textAlign = TextAlign.Center)
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        // COMENTARIO. TODO: Esto deberia estar en la cuenta de otros usuarios, no la propia.
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp)
        ) {
            Button( // TODO: Funcionalidad del botón.
                onClick = { appViewModel.restartBoolean() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Seguir")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        // COMENTARIO.
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // COMENTARIO.
            Text("Mis recetas")

            // COMENTARIO.
            Text("|")

            // COMENTARIO.
            Text("Guardadas")

            // COMENTARIO.
            Text("|")

            // COMENTARIO.
            Text("¿ALGO?")
        }
    }
}