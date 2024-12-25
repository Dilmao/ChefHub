package com.example.chefhub.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import com.example.chefhub.db.dao.CategoriesDao
import com.example.chefhub.db.repository.CategoriesRepository
import com.example.chefhub.navigation.AppScreens
import com.example.chefhub.scaffold.MyMainBottomBar
import com.example.chefhub.screens.components.SimpleTextField
import com.example.chefhub.ui.AppUiState
import com.example.chefhub.ui.AppViewModel

@Composable
fun ModifyRecipeScreen(navController: NavController, appViewModel: AppViewModel) {
    // Estructura de la pantalla.
    Scaffold(
        bottomBar = { MyMainBottomBar("AddRecipe", navController) }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            // Contenido principal de ModifyRecipe.
            ModifyRecipeContent(navController, appViewModel)
        }
    }
}

@Composable
private fun ModifyRecipeContent(navController: NavController, appViewModel: AppViewModel) {
    // Se obtiene el contexto y el estado de la UI.
    val appUiState by appViewModel.appUiState.collectAsState()
    val context = LocalContext.current

    // Diseño de la pantalla.
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(20.dp, bottom = 80.dp)
    ) {
        // Sección de nombre de la receta.
        item { ModifyRecipeNameSection(appUiState, appViewModel) }
        item { Spacer(Modifier.height(30.dp)) }

        // Sección de categorías.
        item { AddCategorySection(appUiState, appViewModel) }
        item { Spacer(Modifier.height(30.dp)) }

        // Sección de dificultad.
        item { AddDificultySection(appUiState, appViewModel) }
        item { Spacer(Modifier.height(30.dp)) }

        // Sección de ingredientes.
        item { ModifyIngredientsSection(appUiState, appViewModel) }
        item { Spacer(Modifier.height(30.dp)) }

        // Sección de instrucciones.
        item { ModifyInstructionsSection(appUiState, appViewModel) }
        item { Spacer(Modifier.height(30.dp)) }

        // Sección de tiempo de preparación.
        item { ModifyPrepTimeSection(appUiState, appViewModel) }
        item { Spacer(Modifier.height(30.dp)) }

        // Sección de tiempo de cocción.
        item { ModifyCookTimeSection(appUiState, appViewModel) }
        item { Spacer(Modifier.height(30.dp)) }

        // Sección de raciones.
        item { ModifyServingsSection(appUiState, appViewModel) }
    }

    // Botón para guardar la receta.
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().padding(20.dp)
    ) {
        Button(onClick = {
            appViewModel.saveRecipe(context, "save") { saveSuccesfull ->
                if (saveSuccesfull) {
                    navController.navigate(AppScreens.AccountScreen.route)
                }
            }
        }) {
            Text("Guardar")
        }
    }
}

// Sección para el nombre de la receta.
@Composable
private fun ModifyRecipeNameSection(appUiState: AppUiState, appViewModel: AppViewModel) {
    Text("Nombre: ", fontWeight = FontWeight.Bold, fontSize = 20.sp)

    SimpleTextField(
        value = appUiState.recipe.title,
        onValueChange = { appViewModel.onRecipeChanged(it, "title") },
        label = "Nombre",
        required = true
    )
}

// Sección para la categoria de la receta.
@Composable
private fun AddCategorySection(appUiState: AppUiState, appViewModel: AppViewModel) {
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(appUiState.recipe.categoryName ?: "") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Categorias: ", fontWeight = FontWeight.Bold, fontSize = 20.sp)

        // Botón para desplegar el menú de categorías.
        Button(onClick = { expanded = true }) {
            Text(text = selectedCategory.ifEmpty { "Selecciona una categoria" })
        }

        // Menú desplegable.
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            appUiState.categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category.categoryName) },
                    onClick = {
                        selectedCategory = category.categoryName
                        println("Categoria: $selectedCategory | $category")
                        appViewModel.onRecipeChanged(selectedCategory, "category")
                        expanded = false
                    }
                )
            }
        }
    }
}

// Sección para la dificultad de la receta.
@Composable
private fun AddDificultySection(appUiState: AppUiState, appViewModel: AppViewModel) {
    val dificulties = listOf("Muy fácil", "Fácil", "Medio", "Difícil", "Muy difícil")
    var expanded by remember { mutableStateOf(false) }
    var selectedDificulty by remember { mutableStateOf(appUiState.recipe.dificulty ?: "") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Dificultad: ", fontWeight = FontWeight.Bold, fontSize = 20.sp)

        Box {
            // Botón para desplegar el menú de dificultades.
            Button(onClick = { expanded = true }) {
                Text(text = selectedDificulty.ifEmpty { "Selecciona una dificultad" })
            }

            // Menú desplegable.
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                dificulties.forEach { dificulty ->
                    DropdownMenuItem(
                        text = { Text(dificulty) },
                        onClick = {
                            selectedDificulty = dificulty
                            appViewModel.onRecipeChanged(dificulty, "dificulty")
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

// Sección para los ingredientes de la receta.
@Composable
private fun ModifyIngredientsSection(appUiState: AppUiState, appViewModel: AppViewModel) {
    Text("Ingredientes", fontWeight = FontWeight.Bold, fontSize = 20.sp)

    // Se muestran los ingredientes existentes.
    appUiState.recipe.ingredients.forEachIndexed { index, ingredient ->
        SimpleTextField(
            value = ingredient,
            onValueChange = { appViewModel.onMutableListChanged(it, index, appUiState.recipe.ingredients, "Ingredient") },
            label = "${index + 1}º Ingrediente"
        )
    }

    // Botón para añadir más ingredientes.
    Button(onClick = {
        appViewModel.onMutableListAddElement(appUiState.recipe.ingredients, "Ingredient")
    }) {
        Icon(Icons.Filled.Add, "Añadir ingrediente")
    }
}

// Sección para las instrucciones de la receta.
@Composable
private fun ModifyInstructionsSection(appUiState: AppUiState, appViewModel: AppViewModel) {
    Text("Instrucciones: ", fontWeight = FontWeight.Bold, fontSize = 20.sp)

    // Se muestran las instrucciones existentes.
    appUiState.recipe.instructions.forEachIndexed { index, instruction ->
        SimpleTextField(
            value = instruction,
            onValueChange = { appViewModel.onMutableListChanged(it, index, appUiState.recipe.instructions, "Instruction") },
            label = "${index + 1}º Instrucción"
        )
    }

    // Botón para añadir más instrucciones
    Button(onClick = {
        appViewModel.onMutableListAddElement(appUiState.recipe.instructions, "Instruction")
    }) {
        Icon(Icons.Filled.Add, "Añadir instrucciones")
    }
}

// Sección para el tiempo de preparación de la receta.
@Composable
private fun ModifyPrepTimeSection(appUiState: AppUiState, appViewModel: AppViewModel) {
    Text("Tiempo de preparación: ", fontWeight = FontWeight.Bold, fontSize = 20.sp)

    SimpleTextField(
        value = appUiState.prepHour.toString(),
        onValueChange = { appViewModel.onRecipeChanged(it.toIntOrNull() ?: 0, "prepHour") },
        label = "Hora/s"
    )
    Spacer(Modifier.height(20.dp))

    SimpleTextField(
        value = appUiState.prepMin.toString(),
        onValueChange = { appViewModel.onRecipeChanged(it.toIntOrNull() ?: 0, "prepMin") },
        label = "Minuto/s"
    )
}

// Sección para el tiempo de cocción de la receta.
@Composable
private fun ModifyCookTimeSection(appUiState: AppUiState, appViewModel: AppViewModel) {
    Text("Tiempo de cocción: ", fontWeight = FontWeight.Bold, fontSize = 20.sp)

    SimpleTextField(
        value = appUiState.cookHour.toString(),
        onValueChange = { appViewModel.onRecipeChanged(it.toIntOrNull() ?: 0, "cookHour") },
        label = "Hora/s"
    )
    Spacer(Modifier.height(20.dp))

    SimpleTextField(
        value = appUiState.cookMin.toString(),
        onValueChange = { appViewModel.onRecipeChanged(it.toIntOrNull() ?: 0, "cookMin") },
        label = "Minuto/s"
    )
}

// Sección para las raciones de la receta.
@Composable
private fun ModifyServingsSection(appUiState: AppUiState, appViewModel: AppViewModel) {
    Text("Raciones: ", fontWeight = FontWeight.Bold, fontSize = 20.sp)

    SimpleTextField(
        value = appUiState.servings.toString(),
        onValueChange = { appViewModel.onRecipeChanged(it.toIntOrNull() ?: 0, "servings") },
        label = "Raciones"
    )
}
