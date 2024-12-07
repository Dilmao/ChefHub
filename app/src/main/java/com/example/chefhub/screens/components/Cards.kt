package com.example.chefhub.screens.components

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chefhub.R
import com.example.chefhub.db.data.Recipes
import com.example.chefhub.db.data.Users
import com.example.chefhub.ui.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeCard(recipe: Recipes, onClick: () -> Unit) {
    // COMENTARIO.
    val prepHour = recipe.prepTime?.div(60) ?: 0
    val prepMin = recipe.prepTime?.rem(60) ?: 0
    val cookHour = recipe.cookTime?.div(60) ?: 0
    val cookMin = recipe.cookTime?.rem(60) ?: 0

    // COMENTARIO.
    Card(
        colors = CardDefaults.cardColors(Color.Gray),
        modifier = Modifier.fillMaxWidth(),
        onClick = { onClick() }
    ) {
        // COMENTARIO.
        Row {
            // COMENTARIO.
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_no_bg),
                    contentDescription = "Receta",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
            }

            // COMENTARIO.
            Column {
                Text(
                    text = recipe.title,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                Text(text = "Prep time: ${prepHour}:${prepMin}")
                Text(text = "Cook time ${cookHour}:${cookMin}")
                Text(text = "Servings: " + recipe.servings)

                // COMENTARIO.
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }

    Spacer(modifier = Modifier.height(10.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCard(user: Users, onClick: () -> Unit) {
    // COMENTARIO.
    Card(
        colors = CardDefaults.cardColors(Color.Gray),
        modifier = Modifier.fillMaxWidth(),
        onClick = { onClick() }
    ) {
        // COMENTARIO.
        Row {
            // COMENTARIO.
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icono_usuario_estandar),
                    contentDescription = "Receta",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
            }

            // COMENTARIO.
            Column {
                Text(
                    text = user.userName,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                user.bio?.let { Text(text = it) }

                // COMENTARIO.
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }

    Spacer(modifier = Modifier.height(10.dp))
}
