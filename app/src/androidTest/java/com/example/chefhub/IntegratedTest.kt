package com.example.chefhub

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import org.junit.Rule
import org.junit.Test

class IntegratedTest {
    @get:Rule
    val rule = createComposeRule()

    // Test Alert.
    @Test
    fun testAlert() {
        rule.setContent {
            var showAlert by remember { mutableStateOf(true) }
            if (showAlert) {
                AlertDialog(
                    modifier = Modifier.testTag("alert"),
                    title = { Text("Titulo prueba", modifier = Modifier.testTag("title")) },
                    text = { Text("Mensaje prueba", modifier = Modifier.testTag("message")) },
                    onDismissRequest = { showAlert = false },
                    confirmButton = {
                        TextButton(onClick = { showAlert = false }, modifier = Modifier.testTag("button")) {
                            Text("Aceptar")
                        }
                    }
                )
            }
        }

        // Verificacinoes.
        rule.onNodeWithTag("alert").assertIsDisplayed()
        rule.onNodeWithTag("title").assertIsDisplayed()
        rule.onNodeWithTag("message").assertIsDisplayed()
        rule.onNodeWithTag("button").assertIsDisplayed()
        rule.onNodeWithTag("button").performClick()
        rule.onNodeWithTag("alert").assertDoesNotExist()
    }

    // Test Buttons.
    @Test
    fun testFollowButtonStateToggle() {
        rule.setContent {
            var isFollowed by remember { mutableStateOf(false) }

            Button(
                modifier = Modifier.testTag("button"),
                onClick = { isFollowed = !isFollowed },
            ) {
                if (isFollowed) {
                    Text("Siguiendo", modifier = Modifier.testTag("following"))
                } else {
                    Text("Seguir", modifier = Modifier.testTag("follow"))
                }
            }
        }

        // Verificaciones.
        rule.onNodeWithTag("button").assertIsDisplayed()
        rule.onNodeWithTag("button").assertTextEquals("Seguir")
        rule.onNodeWithTag("button").performClick()
        rule.onNodeWithTag("button").assertTextEquals("Siguiendo")
        rule.onNodeWithTag("button").performClick()
        rule.onNodeWithTag("button").assertTextEquals("Seguir")
    }

    // Test Cards.
    @Test
    fun testRecipeCard() {
        rule.setContent {
            val prepHour = 1
            val prepMin = 30
            val cookHour = 0
            val cookMin = 45

            Card(modifier = Modifier.testTag("card")) {
                Row(modifier = Modifier.testTag("row")) {
                    Column(modifier = Modifier.testTag("columnImage")) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_no_bg),
                            contentDescription = "Imagen de la receta",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.testTag("image")
                        )
                    }

                    Column(modifier = Modifier.testTag("columnInfo")) {
                        Text(
                            text = "Recipe 1",
                            modifier = Modifier.testTag("recipeName")
                        )
                        Text(
                            text = "Tiempo de preparación: ${prepHour}:${prepMin}",
                            modifier = Modifier.testTag("prepTime")
                        )
                        Text(
                            text = "Tiempo de cocción: ${cookHour}:${cookMin}",
                            modifier = Modifier.testTag("cookTime")
                        )
                        Text(
                            text = "Porciones: 1",
                            modifier = Modifier.testTag("servings")
                        )
                    }
                }
            }
        }

        // Verificaciones.
        rule.onNodeWithTag("card").assertIsDisplayed()
        rule.onNodeWithTag("image").assertIsDisplayed()
        rule.onNodeWithTag("recipeName").assertIsDisplayed()
        rule.onNodeWithTag("prepTime").assertIsDisplayed()
        rule.onNodeWithTag("cookTime").assertIsDisplayed()
        rule.onNodeWithTag("servings").assertIsDisplayed()
    }

    // Test TestFields.
    @Test
    fun testPasswordVisibilityToggle() {
        rule.setContent {
            var showPassword by remember { mutableStateOf(false) }
            var value by remember { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier.testTag("password"),
                value = value,
                onValueChange = { value = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        modifier = Modifier.testTag("iconButton"),
                        onClick = { showPassword = !showPassword }
                    ) {
                        Icon(
                            modifier = Modifier.testTag("icon"),
                            painter = painterResource(id = R.drawable.ojo_ocultar),
                            contentDescription = "Mostrar/Ocultar contraseña",
                        )
                    }
                }
            )
        }

        // Verificaciones.
        rule.onNodeWithTag("iconButton").assertIsDisplayed()
        rule.onNodeWithTag("password").performTextInput("password123")
        rule.onNodeWithTag("password").assertTextEquals("•••••••••••")
        rule.onNodeWithTag("iconButton").performClick()
        rule.onNodeWithTag("password").assertTextEquals("password123")
    }
}