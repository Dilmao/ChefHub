package com.example.chefhub.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chefhub.scaffold.MyMainBottomBar
import com.example.chefhub.screens.components.PersonalizedLazyColumn
import com.example.chefhub.screens.components.SimpleTextField
import com.example.chefhub.ui.AppViewModel

@Composable
fun AddRecipeScreen(navController: NavController, appViewModel: AppViewModel) {
    // COMENTARIO.
    Scaffold(
        topBar = {},
        bottomBar = { MyMainBottomBar("AddRecipe", navController) }
    ) { paddingValues ->
        // COMENTARIO.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // COMENTARIO.
            AddRecipeScreenContent(navController, appViewModel)
        }
    }
}

@Composable
fun AddRecipeScreenContent(navController: NavController, appViewModel: AppViewModel) {
    // COMENTARIO.
    val appUiState by appViewModel.appUiState.collectAsState()
    val context = LocalContext.current

    // COMENTARIO.
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        // TODO: Arreglar los spacer
        // COMENTARIO.
        Text("Nombre: ")
        Spacer(modifier = Modifier.height(10.dp))

        // COMENTARIO.
        SimpleTextField(
            value = appUiState.recipeTitle,
            onValueChange = { appViewModel.onRecipeTitleChanged(it) },
            label = "Nombre",
            required = true
        )
        Spacer(modifier = Modifier.height(20.dp))

        // COMENTARIO.
        Text("Ingredientes: ")
        Spacer(modifier = Modifier.height(0.dp))

        // COMENTARIO.
        PersonalizedLazyColumn(
            list = appUiState.ingredientList,
            listName = "Ingredient",
            label = "º Ingrediente",
            appViewModel = appViewModel
        )
        Spacer(modifier = Modifier.height(15.dp))

        // COMENTARIO.
        Button(onClick = { appViewModel.onMutableListAddElement(appUiState.ingredientList, "Ingredient") }) {
            Icon(Icons.Filled.Add, "Añadir ingrediente")
        }
        Spacer(modifier = Modifier.height(20.dp))

        // COMENTARIO.
        Text("Instrucciones: ")
        Spacer(modifier = Modifier.height(10.dp))

        // COMENTARIO.
        PersonalizedLazyColumn(
            list = appUiState.instructionsList,
            listName = "Instruction",
            label = "ª Instrucción",
            appViewModel = appViewModel
        )
        Spacer(modifier = Modifier.height(15.dp))

        // COMENTARIO.
        Button(onClick = { appViewModel.onMutableListAddElement(appUiState.instructionsList, "Instruction") }) {
            Icon(Icons.Filled.Add, "Añadir instrucciones")
        }

        // TODO: Botón para guardar la receta.
        Button(onClick = { appViewModel.onSaveRecipe() }) {
            Text("Guardar receta")
        }
    }
}
