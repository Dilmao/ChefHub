package com.example.chefhub.screens

import android.content.Context
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
    // COMENTARIO.
    Scaffold(
        bottomBar = { MyMainBottomBar("Account", navController) }
    ) { paddingValues ->
        // COMENTARIO.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // COMENTARIO.
            RecipeScreenContent(navController, appViewModel)
        }
    }
}

@Composable
fun RecipeScreenContent(navController: NavController, appViewModel: AppViewModel) {
    val appUiState by appViewModel.appUiState.collectAsState()
    val context = LocalContext.current
    val recipe = appUiState.recipe

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        /** Sección: Imagen de la receta **/
        item { RecipeImageSection() }

        /** Sección: Título y botón para guardar **/
        item { TitleAndSaveButton(recipe, appViewModel, context, appUiState) }

        /** Separador sombreado (TODO) **/

        /** Sección: Información adicional **/
        item { RecipeDetailsSection(recipe, appUiState) }

        /** Sección: Lista de ingredientes **/
        item { HeaderItem("Ingredientes:") }
        items(recipe.ingredients.size) { index ->
            ListItem(recipe.ingredients[index])
        }
        item { Spacer(Modifier.height(20.dp)) }

        item { HeaderItem("Instrucciones:") }
        items(recipe.instructions.size) { index ->
            ListItem(recipe.instructions[index])
        }
        item { Spacer(Modifier.height(20.dp)) }

        /** Sección: Botones de edición y eliminación **/
        if (recipe.userId == appUiState.user.userId) {
            item {
                EditAndDeleteButtons(recipe, appViewModel, navController)
            }
        }
    }
}

@Composable
fun RecipeImageSection() {
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
    Spacer(Modifier.height(20.dp))
}

@Composable
fun TitleAndSaveButton(
    recipe: Recipes,
    appViewModel: AppViewModel,
    context: Context,
    appUiState: AppUiState
) {
    var recipeSaved by remember { mutableStateOf(appUiState.favorites.any { it.recipeId == recipe.recipeId }) }
    var action by remember { mutableStateOf("save") }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
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
                modifier = Modifier
                    .size(40.dp)
                    .clip(RectangleShape)
            )
        }
    }
    Spacer(Modifier.height(20.dp))
}

@Composable
fun RecipeDetailsSection(recipe: Recipes, appUiState: AppUiState) {
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
fun RecipeDetailRow(
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
    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun HeaderItem(header: String) {
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
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
fun EditAndDeleteButtons(
    recipe: Recipes,
    appViewModel: AppViewModel,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
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
