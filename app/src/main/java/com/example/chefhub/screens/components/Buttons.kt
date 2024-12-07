package com.example.chefhub.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.chefhub.db.data.Recipes

@Composable
fun SimpleButton(
    texto: String,          // Texto que se motrara en el botón.
    onClick: () -> Unit,    // Acción que se ejecutará cuando el botón sea presionado.
) {
    Button(onClick = onClick) {
        Text(text = texto)
    }
}

@Composable
fun InvisibleButton(
    texto: String,          // Texto que se motrara en el botón.
    onClick: () -> Unit,    // Acción que se ejecutará cuando el botón sea presionado.
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Black
        )
    ) {
        Text(text = texto)
    }
}

@Composable
fun FollowButton(
    onClick: () -> Unit,
    isFollowed: Boolean,
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isFollowed) Color.Gray else Color.Blue,
            contentColor = Color.White
        )
    ) {
        if (isFollowed) {
            Text("Siguiendo")
        } else {
            Text("Seguir")
        }
    }
}

@Composable
fun RecipeButton(
    texto: String,          // Texto que se mostrará en el botón.
    type: String,           // Indica el tipo para determinar el color.
    onClick: () -> Unit,    // Acción que se ejecutará cuando el botón sea presionado.
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (type.equals("edit", ignoreCase = true)) Color.Blue else Color.Red,
            contentColor = Color.White
        )
    ) {
        Text(text = texto)
    }
}

@Composable
fun SettingButton( // TODO: Modificar diseño del botón
    title: String,          // Texto que se motrara en el botón.
    onClick: () -> Unit,    // Acción que se ejecutará cuando el botón sea presionado.
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF00796B),
            contentColor = Color.White
        ),
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title)
            Text(text = ">")
        }
    }
}