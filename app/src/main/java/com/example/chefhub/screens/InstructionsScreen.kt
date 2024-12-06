package com.example.chefhub.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chefhub.scaffold.MyMainBottomBar
import com.example.chefhub.ui.AppViewModel

@Composable
fun InstructionsScreen(navController: NavController, appViewModel: AppViewModel) {
    // Estructura principal de la pantalla de recetas.
    Scaffold(
        topBar = {},
        bottomBar = { MyMainBottomBar("Account", navController) }
    ) { paddingValues ->
        // Caja que ocupa el tamaño disponible aplicando un padding.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Contenido del cuerpo de la pantalla de recetas.
            InstructionsScreenContent(navController, appViewModel)
        }
    }
}

@Composable
fun InstructionsScreenContent(navController: NavController, appViewModel: AppViewModel) {
    // COMENTARIO.
    val appUiState by appViewModel.appUiState.collectAsState()
    val context = LocalContext.current
    val instructionList = appUiState.recipe.instructions

    // COMENTARIO.
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        items(instructionList.size) { index ->
            Text(
                "${index + 1}ª Instrucción",
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp
            )
            Text(instructionList[index])
        }
    }
}
