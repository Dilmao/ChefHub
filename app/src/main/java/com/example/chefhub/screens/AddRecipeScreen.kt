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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chefhub.navigation.AppScreens
import com.example.chefhub.scaffold.MyMainBottomBar
import com.example.chefhub.screens.components.SimpleTextField
import com.example.chefhub.ui.AppViewModel

@Composable
fun AddRecipeScreen(navController: NavController, appViewModel: AppViewModel) {
    // Estructura principal de la pantalla de recetas.
    Scaffold(
        topBar = {},
        bottomBar = { MyMainBottomBar("AddRecipe", navController) }
    ) { paddingValues ->
        // Caja que ocupa el tamaño disponible aplicando un padding.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Contenido del cuerpo de la pantalla de recetas.
            AddRecipeScreenContent(navController, appViewModel)
        }
    }
}

@Composable
fun AddRecipeScreenContent(navController: NavController, appViewModel: AppViewModel) {
    // TODO: Intentar hacer más atractivo a la vista.
    // Se obtiene el contexto y el estado de la UI.
    val appUiState by appViewModel.appUiState.collectAsState()
    val context = LocalContext.current
    var loadedRecipes by remember { mutableStateOf(false) }

    if (!loadedRecipes) {
        appViewModel.resetRecipeValues()
        loadedRecipes = true
    }

    // Estructura en columna para alinear los elementos
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, bottom = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Seccion para introducir el nombre de la receta.
        item { Text("Nombre: ", fontWeight = FontWeight.Bold, fontSize = 20.sp) }
        item {
            SimpleTextField(
                value = appUiState.recipe.title,
                onValueChange = { appViewModel.onRecipeChanged(it, "title") },
                label = "Nombre",
                required = true
            )
        }
        item { Spacer(modifier = Modifier.height(30.dp)) }


        // TODO: Seccion para introducir la categoria a la receta


        // Sección para introducir los ingredientes de la receta.
        item { Text("Ingredientes: ", fontWeight = FontWeight.Bold, fontSize = 20.sp) }
        items(appUiState.recipe.ingredients.size) { index ->
            SimpleTextField(
                value = appUiState.recipe.ingredients[index],
                onValueChange = { appViewModel.onMutableListChanged(it, index, appUiState.recipe.ingredients, "Ingredient") },
                label = "${index + 1}º Ingrediente"
            )
        }
        item { Spacer(modifier = Modifier.height(10.dp)) }

        // Botón para añadir ingredientes
        item {
            Button(onClick = { appViewModel.onMutableListAddElement(appUiState.recipe.ingredients, "Ingredient") }) {
                Icon(Icons.Filled.Add, "Añadir ingrediente")
            }
        }
        item { Spacer(modifier = Modifier.height(30.dp)) }


        // Sección para introducir las instrucciones de la receta.
        item { Text("Instrucciones: ", fontWeight = FontWeight.Bold, fontSize = 20.sp) }
        items(appUiState.recipe.instructions.size) { index ->
            SimpleTextField(
                value = appUiState.recipe.instructions[index],
                onValueChange = { appViewModel.onMutableListChanged(it, index, appUiState.recipe.instructions, "Instruction") },
                label = "${index + 1}ª Instrucción"
            )
        }
        item { Spacer(modifier = Modifier.height(10.dp)) }

        // Botón para añadir instrucciones
        item {
            Button(onClick = {
                appViewModel.onMutableListAddElement(appUiState.recipe.instructions, "Instruction")
                println("Instrucciones: ${appUiState.recipe.instructions}")
            }) {
                Icon(Icons.Filled.Add, "Añadir instrucciones")
            }
        }


        // Seccion para introducir el tiempo de preparación de la receta.
        item { Text("Tiempo de preparación: ", fontWeight = FontWeight.Bold, fontSize = 20.sp) }
        item {
            SimpleTextField(
                value = appUiState.prepHour.toString(),
                onValueChange = {
                    if (it.toIntOrNull() != null) {
                        appViewModel.onRecipeChanged(it.toInt(), "prepHour")
                    } else {
                        appViewModel.onRecipeChanged(0, "prepHour")
                    }
                },
                label = "Hora/s"
            )

            Spacer(Modifier.height(20.dp))

            SimpleTextField(
                value = appUiState.prepMin.toString(),
                onValueChange = {
                    if (it.toIntOrNull() != null) {
                        appViewModel.onRecipeChanged(it.toInt(), "prepMin")
                    } else {
                        appViewModel.onRecipeChanged(0, "prepMin")
                    }
                },
                label = "Minuto/s"
            )
        }
        item { Spacer(modifier = Modifier.height(30.dp)) }


        // Seccion para introducir el tiempo de cocción de la receta.
        item { Text("Tiempo de cocción: ", fontWeight = FontWeight.Bold, fontSize = 20.sp) }
        item {
            SimpleTextField(
                value = appUiState.cookHour.toString(),
                onValueChange = {
                    if (it.toIntOrNull() != null) {
                        appViewModel.onRecipeChanged(it.toInt(), "cookHour")
                    } else {
                        appViewModel.onRecipeChanged(0, "cookHour")
                    }
                },
                label = "Hora/s"
            )

            Spacer(Modifier.height(20.dp))

            SimpleTextField(
                value = appUiState.cookMin.toString(),
                onValueChange = {
                    if (it.toIntOrNull() != null) {
                        appViewModel.onRecipeChanged(it.toInt(), "cookMin")
                    } else {
                        appViewModel.onRecipeChanged(0, "cookMin")
                    }
                },
                label = "Minuto/s"
            )
        }
        item { Spacer(modifier = Modifier.height(30.dp)) }

        // Seccion para introducir las raciones de la receta.
        item { Text("Raciones: ", fontWeight = FontWeight.Bold, fontSize = 20.sp) }
        item {
            SimpleTextField(
                value = appUiState.servings.toString(),
                onValueChange = {
                    if (it.toIntOrNull() != null) {
                        appViewModel.onRecipeChanged(it.toInt(), "servings")
                    } else {
                        appViewModel.onRecipeChanged(0, "servings")
                    }
                },
                label = "Hora/s"
            )
        }
        item { Spacer(modifier = Modifier.height(30.dp)) }
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
        Button(onClick = {
            appViewModel.saveRecipe(context, "create") { createSuccesfull ->
                if (createSuccesfull) {
                    navController.navigate(AppScreens.AccountScreen.route)
                }
            }
        }) {
            Text("Crear")
        }
    }
}
