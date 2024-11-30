package com.example.chefhub.screens.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chefhub.R

@Composable
fun SimpleTextField(
    value: String,                      // Valor del campo de texto.
    onValueChange: (String) -> Unit,    // Función para actualizar el valor del campo de texto.
    label: String,                      // Etiqueta que se muestra en el campo de texto.
    required: Boolean = false           // Indica si el campo es obligatorio.
) {
    // Campo de entrada de texto.
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Label(text = label, required = required) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@Composable
fun PasswordTextField(
    value: String,                      // Valor del campo de contraseña.
    onValueChange: (String) -> Unit,    // Función para actualizar el valor del campo de contraseña.
    label: String,                      // Etiqueta que se muestra en el campo de contraseña.
    required: Boolean                   // Indica si el campo es obligatorio.
) {
    // Variable para controlar si la contraseña es visible o no.
    var showPassword by rememberSaveable { mutableStateOf(false) }

    // Campo de entrada de contraseña.
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Label(text = label, required = required) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation =
        if (showPassword) {
            // Muestra la contraseña en texto claro si showPassword es true.
            VisualTransformation.None
        } else {
            // Aplica una transformación de visualización de contraseña si showPassword es false.
            PasswordVisualTransformation()
        },
        trailingIcon = {
            // Icono para mostrar/ocultar la contraseña.
            IconButton(onClick = { showPassword = !showPassword }) {
                Icon(
                    painter = painterResource(id = R.drawable.ojo_ocultar),
                    contentDescription = "Mostrar/Ocultar contraseña",
                    modifier = Modifier.size(50.dp),
                )
            }
        }
    )
}

@Composable
private fun Label(
    text: String,
    required: Boolean
) {
    // Composición de la etiqueta.
    Row {
        Text(text = text)
        // Si el campo es obligatorio, se muestra un asterisco rojo.
        if (required) {
            Text(
                text = "*",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(end = 2.dp)
            )
        }
    }
}