package com.example.chefhub.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chefhub.scaffold.MyMainBottomBar
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
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, bottom = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // COMENTARIO.
        item { Text("Nombre: ", fontWeight = FontWeight.Bold, fontSize = 20.sp) }
        item { SimpleTextField(
            value = appUiState.recipeTitle,
            onValueChange = { appViewModel.onRecipeTitleChanged(it) },
            label = "Nombre",
            required = true
        ) }
        item { Spacer(modifier = Modifier.height(30.dp)) }

        // Sección de ingredientes
        item { Text("Ingredientes: ", fontWeight = FontWeight.Bold, fontSize = 20.sp) }
        items(appUiState.ingredientList.size) { index ->
            SimpleTextField(
                value = appUiState.ingredientList[index],
                onValueChange = { appViewModel.onMutableListChanged(it, index, appUiState.ingredientList, "Ingredient") },
                label = "${index + 1}º Ingrediente",
                required = false
            )
        }
        item { Spacer(modifier = Modifier.height(10.dp)) }


        // Botón para añadir ingredientes
        item {
            Button(onClick = { appViewModel.onMutableListAddElement(appUiState.ingredientList, "Ingredient") }) {
                Icon(Icons.Filled.Add, "Añadir ingrediente")
            }
        }
        item { Spacer(modifier = Modifier.height(30.dp)) }

        // Sección de instrucciones
        item { Text("Instrucciones: ", fontWeight = FontWeight.Bold, fontSize = 20.sp) }
        items(appUiState.instructionsList.size) { index ->
            SimpleTextField(
                value = appUiState.instructionsList[index],
                onValueChange = { appViewModel.onMutableListChanged(it, index, appUiState.instructionsList, "Instruction") },
                label = "${index + 1}ª Instrucción",
                required = false
            )
        }
        item { Spacer(modifier = Modifier.height(10.dp)) }

        // Botón para añadir instrucciones
        item {
            Button(onClick = { appViewModel.onMutableListAddElement(appUiState.instructionsList, "Instruction") }) {
                Icon(Icons.Filled.Add, "Añadir instrucciones")
            }
        }
    }

    // COMENTARIO.
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        // Botón para guardar la receta.
        Button(onClick = { appViewModel.onSaveRecipe() }) {
            Text("Guardar receta")
        }
    }
}
