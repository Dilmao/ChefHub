package com.example.chefhub.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chefhub.R
import com.example.chefhub.db.data.Categories
import com.example.chefhub.db.data.Recipes
import com.example.chefhub.db.data.Users

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeCard(recipe: Recipes, onClick: () -> Unit) {
    // Calcula las horas y minutos para los tiempos de preparación y cocción
    val prepHour = recipe.prepTime?.div(60) ?: 0
    val prepMin = recipe.prepTime?.rem(60) ?: 0
    val cookHour = recipe.cookTime?.div(60) ?: 0
    val cookMin = recipe.cookTime?.rem(60) ?: 0

    // Diseño del componente de la tarjeta de receta
    Card(
        colors = CardDefaults.cardColors(Color.Gray),
        modifier = Modifier.fillMaxWidth(),
        onClick = { onClick() }
    ) {
        Row {
            // Imagen circular representando la receta
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_no_bg),
                    contentDescription = "Imagen de la receta",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(100.dp).clip(CircleShape)
                )
            }

            // Detalles de la receta
            Column {
                Text(
                    text = recipe.title,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth().padding(10.dp)
                )
                // Muestra el tiempo de preparación, cocción y las porciones
                Text(text = "Tiempo de preparación: ${prepHour}:${prepMin}")
                Text(text = "Tiempo de cocción: ${cookHour}:${cookMin}")
                Text(text = "Porciones: ${recipe.servings}")
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }

    Spacer(modifier = Modifier.height(10.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCard(user: Users, onClick: () -> Unit) {
    // Tarjeta que muestra la información de un usuario
    Card(
        colors = CardDefaults.cardColors(Color.Gray),
        modifier = Modifier.fillMaxWidth(),
        onClick = { onClick() }
    ) {
        Row {
            // Imagen circular representando al usuario
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icono_usuario_estandar),
                    contentDescription = "Avatar del usuario",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(50.dp).clip(CircleShape)
                )
            }

            // Detalles del usuario
            Column {
                Text(
                    text = user.userName,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth().padding(10.dp)
                )
                user.bio?.let { Text(text = it) }
            }
        }
    }

    Spacer(modifier = Modifier.height(10.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCard(category: Categories, onClick: () -> Unit) {
    // Tarjeta que muestra la información de una categoría
    Card(
        colors = CardDefaults.cardColors(Color.Gray),
        modifier = Modifier.fillMaxWidth(),
        onClick = { onClick() }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(20.dp)
        ) {
            // Imagen circular representando la categoría
            Image(
                painter = painterResource(id = R.drawable.logo_no_bg),
                contentDescription = "Icono de la categoría",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(50.dp).clip(CircleShape)
            )

            // Nombre de la categoría
            Text(
                text = category.categoryName,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth().padding(10.dp)
            )
        }
    }

    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun MiniCategoryCard(categoryName: String) {
    // Tarjeta que muestra la información de una categoría
    Card(
        colors = CardDefaults.cardColors(Color.Gray),
    ) {
        // Nombre de la categoría
        Text(
            text = categoryName,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(10.dp)
        )
    }
}
