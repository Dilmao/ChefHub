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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chefhub.R
import com.example.chefhub.db.data.Recipes
import com.example.chefhub.navigation.AppScreens
import com.example.chefhub.scaffold.MyMainBottomBar
import com.example.chefhub.screens.components.RecipeButton
import com.example.chefhub.ui.AppUiState
import com.example.chefhub.ui.AppViewModel

@Composable
fun RecipeScreen(navController: NavController, appViewModel: AppViewModel) {
    // Estructura de la pantalla.
    Scaffold(
        bottomBar = { MyMainBottomBar("", navController) }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            // Contenido principal de RecipeScreen.
            RecipeContent(navController, appViewModel)
        }
    }
}

@Composable
private fun RecipeContent(navController: NavController, appViewModel: AppViewModel) {
    // Se obtiene el contexto y el estado de la UI.
    val appUiState by appViewModel.appUiState.collectAsState()
    val recipe = appUiState.recipe

    // Diseño de la pantalla.
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxSize().padding(30.dp)
    ) {
        // Sección de imagen de la receta.
        item { RecipeImageSection() }
        item { Spacer(Modifier.height(20.dp)) }

        // Sección de título y botón de guardar.
        item { TitleAndSaveButton(recipe, appViewModel, appUiState) }
        item { Spacer(Modifier.height(20.dp)) }

        // TODO: Separador sombreado

        // Sección de detalles de la receta.
        item { RecipeDetailsSection(recipe, appUiState) }
        item { Spacer(Modifier.height(20.dp)) }

        // Sección de ingredientes.
        item { HeaderItem("Ingredientes:") }
        items(recipe.ingredients.size) { index ->
            ListItem(recipe.ingredients[index])
        }
        item { Spacer(Modifier.height(20.dp)) }

        // Sección de instrucciones.
        item { HeaderItem("Instrucciones:") }
        items(recipe.instructions.size) { index ->
            ListItem(recipe.instructions[index])
        }
        item { Spacer(Modifier.height(20.dp)) }

        // Sección de botones (solamente visibles para el creador de la receta).
        if (recipe.userId == appUiState.user.userId) {
            item { EditAndDeleteButtons(recipe, appViewModel, navController) }
        }
    }
}

@Composable
private fun RecipeImageSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_no_bg),
            contentDescription = "Receta",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RectangleShape)
                .fillMaxWidth()
                .background(Color.LightGray)
        )
    }
}

@Composable
private fun TitleAndSaveButton(
    recipe: Recipes,
    appViewModel: AppViewModel,
    appUiState: AppUiState
) {
    val context = LocalContext.current
    var recipeSaved by remember { mutableStateOf(appUiState.favorites.any { it.recipeId == recipe.recipeId }) }
    var action by remember { mutableStateOf("save") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = recipe.title,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
        )

        IconButton(
            onClick = {
                recipeSaved = !recipeSaved
                action = if (recipeSaved) "save" else "delete"
                appViewModel.onChangeFavorite(recipe, action, context)
            }
        ) {
            Image(
                painter = painterResource(
                    id = if (recipeSaved) R.drawable.saved else R.drawable.save
                ),
                contentDescription = if (recipeSaved) "Receta guardada" else "Guardar receta",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(40.dp).clip(RectangleShape)
            )
        }
    }
}

@Composable
private fun RecipeDetailsSection(recipe: Recipes, appUiState: AppUiState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        RecipeDetailRow(
            icon = R.drawable.hat,
            label = "Dificultad",
            value = "[placeholder]"
        )
        RecipeDetailRow(
            icon = R.drawable.preparation,
            label = "Tiempo de preparación",
            value = "${appUiState.prepHour}:${appUiState.prepMin}"
        )
        RecipeDetailRow(
            icon = R.drawable.timer,
            label = "Tiempo total",
            value = "${appUiState.cookHour}:${appUiState.cookMin}"
        )
        RecipeDetailRow(
            icon = R.drawable.portion,
            label = "Raciones",
            value = "${recipe.servings} unidad/es"
        )
    }
}

@Composable
private fun RecipeDetailRow(
    icon: Int,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = label,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(RectangleShape)
        )
        Spacer(Modifier.width(10.dp))
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(label, color = Color.Gray)
            Text(value)
        }
    }
}

@Composable
private fun HeaderItem(header: String) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            header,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )
    }
    Spacer(Modifier.height(10.dp))
}

@Composable
private fun ListItem(item: String) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
    ) {
        // Icono de viñeta (bullet point)
        Text(
            text = "•",
            fontSize = 25.sp,
            modifier = Modifier.padding(end = 8.dp)
        )
        // Texto del ingrediente/instrucción
        Text(
            text = item,
            fontSize = 16.sp
        )
    }
}

@Composable
private fun EditAndDeleteButtons(
    recipe: Recipes,
    appViewModel: AppViewModel,
    navController: NavController
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth().padding(20.dp)
    ) {
        RecipeButton(
            texto = "Editar",
            type = "edit",
            onClick = { navController.navigate(AppScreens.ModifyRecipeScreen.route) }
        )
        Spacer(Modifier.width(20.dp))
        RecipeButton(
            texto = "Borrar",
            type = "delete",
            onClick = {
                appViewModel.onDeleteRecipe(recipe)
                navController.navigate(AppScreens.AccountScreen.route)
            }
        )
    }
}
