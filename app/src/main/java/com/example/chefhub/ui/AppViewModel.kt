package com.example.chefhub.ui

import android.content.Context
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import com.example.chefhub.screens.components.saveCredentials
import com.example.chefhub.screens.components.showMessage
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel {
    private val _appUiState = MutableStateFlow(AppUiState())
    val appUiState: StateFlow<AppUiState> = _appUiState.asStateFlow()

    /** Reiniciar variables **/
    fun restartBoolean() {
        // Se reinician las variables de tipo boolean.
        _appUiState.update { currentState ->
            currentState.copy(showMessage = false)
        }
    }

    /** Funciones Login **/
    fun onLoginChanged(
        email: String,
        password: String
    ) {
        // Se actualiza el estado de la página LoginScreen.
        _appUiState.update { currentState ->
            currentState.copy(email = email, password = password)
        }
    }

    fun checkLogin (
        context: Context,
        callback: (Boolean) -> Unit
    ) {
        // Se obtiene el correo electrónico y la contraseña del estado actual de la UI.
        val email = appUiState.value.email
        val password = appUiState.value.password

        // Se inicializa la instancia de FirebaseAuth para manejar la autenticación.
        val auth: FirebaseAuth = Firebase.auth

        // Realiza el intento de inicio de sesión con el correo y la contraseña.
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            // Si el inicio de sesión es exitoso, muestra un mensaje de éxito.
            if (task.isSuccessful) {
                saveCredentials(context, email, password)
                showMessage(context = context, mensaje = "Inicio de sesión exitoso.")
            } else {
                // Si falla, muestra un mensaje indicando que el correo o la contraseña son incorrectos.
                showMessage(context = context, mensaje = "El correo electrónico o la contraseña son incorrectos.")
            }
            // Lama al callback con el resultado del inicio de sesión
            callback(task.isSuccessful)
        }
    }

    /** Funciones Register **/
    fun onRegisterChanged(
        newValue: String,
        valueName: String
    ) {
        // Se obtiene el estado actual de la UI.
        val currentState = appUiState.value

        // Se actualiza el estado dependiendo del campo que se este modificando.
        val updatedState = when (valueName) {
            "user" -> currentState.copy(newUsuario = newValue)
            "email" -> currentState.copy(newEmail = newValue)
            "password" -> currentState.copy(newPassword = newValue)
            "confirmPassword" -> currentState.copy(confirmNewPassword = newValue)
            // En caso de que el valor modificado no este entre las opciones, se muestra un mensaje de error inesperado
            else -> currentState.copy(
                messageText = "Error inesperado en RegisterScreen ($valueName)",
                showMessage = true
            ) // TODO: Mejorar el manejo de errores para casos inesperados.
        }

        // Se actualiza el estado de la página RegisterScreen.
        _appUiState.update { updatedState }
    }

    fun checkRegister(
        context: Context,
        callback: (Boolean) -> Unit
    ) {
        // Se obtiene el nuevo usuario, correo electrónico y contraseña desde el estado de la UI.
        val newUsuario = appUiState.value.newUsuario
        val newEmail = appUiState.value.newEmail
        val newPassword = appUiState.value.newPassword

        // Se inicializa FirebaseAuth para crear una nueva cuenta.
        val auth: FirebaseAuth = Firebase.auth

        // Se intenta crear una nueva cuenta de usuario en Firebase con el correo y la conteaseña proporcionados.
        auth.createUserWithEmailAndPassword(newEmail, newPassword).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Si el registro es exitoso, muestra un mensaje de éxito.
                showMessage(context = context, mensaje = "Cuenta creada exitosamente.")

                // TODO: Almacenar la información del usuario en la base de datos (Cloud Firestore).
//                // Se obtiene el UID del usuario recién creado.
//                val userId = auth.currentUser?.uid
//
//                // Se guarda el nombre de usuario en Firestore, junto con otros datos.
//                val user = hashMapOf(
//                    "userId" to userId,
//                    "username" to newUsuario,
//                    "email" to newEmail
//                )
//
//                // COMENTARIO.
//                firestore.collection("users")
//                    .document(userId!!)
//                    .set(user)
//                    .addOnSuccessListener {
//                        // COMENTARIO.
//                        showMessage(context = context, mensaje = "Cuenta creada exitosamente.")
//                    }
//                    .addOnFailureListener {
//                        // COMENTARIO.
//                        showMessage(context = context, mensaje = "Error al guardar el nombre de usuario.")
//                    }
            } else {
                // Si falla el registro, muestra un mensaje indicando que el correo ya esta registrado.
                showMessage(context = context, mensaje = "El correo electrónico ya existe en nuestra base de datos.") // TODO: Mejorar mensaje.
            }
            // Devuelve el resultado del registro.
            callback(task.isSuccessful)
        }
    }

    /** Funciones Password Recovery **/
    fun onRecoveryChanged(
        recoveryEmail: String
    ) {
        // Se actualiza el estado de la página PasswordRecoveryScreen.
        _appUiState.update { currentState ->
            currentState.copy(recoveryEmail = recoveryEmail)
        }
    }

    fun recoverPassword(
        email: String
    ) {
        // Se obtiene la instancia de FirebaseAuth para enviar el correo de restablecimiento de contraseña.
        val auth = FirebaseAuth.getInstance()
        var errorMessage: String

        // Se intenta enviar un correo electrónico para restablecer la contraseña a la dirección proporcionada.
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                // Si el envio del correo fue exitoso, se muestra un mensaje de éxito.
                if (task.isSuccessful) {
                    errorMessage = "Se ha enviado un correo electrónico a la dirección: \"${appUiState.value.recoveryEmail}\".\n Siga las instrucciones en el correo para cambiar la contraseña."
                } else {
                    // Si la tarea falla, se verifica la excepción que provocó el fallo. TODO: No se cuando entra en este caso :v
                    errorMessage = when (val exception = task.exception) {
                        // Si es una excepción de FirebaseAuth.
                        is FirebaseAuthException -> {
                            // Si el error es que no se encontró un usuario con ese correo.
                            if (exception.errorCode == "ERROR_USER_NOT_FOUND") {
                                "No se ha encontrado una cuenta con ese correo electrónico."
                            } else {
                                // En caso de otro error de FirebaseAuth.
                                "Se ha producido un error. Por favor, inténtalo de nuevo."
                            }
                        } else -> {
                            // Si es otro tipo de error no especificado.
                            "Error desconocido. Por favor, intenta más tarde."
                        }
                    }
                }

                // Se actualiza el estado de la UI para mostrar el mensaje de éxito o error correspondiente.
                _appUiState.update { currentState ->
                    currentState.copy(
                        showMessage = true,
                        messageText = errorMessage,
                    )
                }
            }
    }

    /** Funciones Add Recipe **/
    fun onRecipeTitleChanged(recipeTitle: String) {
        // COMENTARIO.
        _appUiState.update { currentState ->
            currentState.copy(recipeTitle = recipeTitle)
        }
    }

    fun onMutableListChanged(
        newValue: String,
        index: Int,
        list: MutableList<String>,
        listName: String
    ) {
        // COMENTARIO.
        val updatedList = list.toMutableList()

        // COMENTARIO.
        updatedList[index] = newValue

        // COMENTARIO.
        _appUiState.value = when (listName) {
            "Ingredient" -> appUiState.value.copy(ingredientList = updatedList)
            "Instruction" -> appUiState.value.copy(instructionsList = updatedList)
            else -> throw IllegalArgumentException("El nombre de la lista '$listName' no es válido.")
        }
    }

    fun onMutableListAddElement(
        list: MutableList<String>,
        listName: String
    ) {
        // COMENTARIO.
        val updatedList = list.toMutableList()

        // COMENTARIO.
        updatedList.add("")

        // COMENTARIO.
        _appUiState.value = when (listName) {
            "Ingredient" -> appUiState.value.copy(ingredientList = updatedList)
            "Instruction" -> appUiState.value.copy(instructionsList = updatedList)
            else -> throw IllegalArgumentException("El nombre de la lista '$listName' no es válido.")
        }
    }

    fun onSaveRecipe() {
        // TODO: Función para guardar una receta.
    }

    /** Funciones AccountScreen **/
    fun onChangeDrawerState() {
        // COMENTARIO.
        var drawerState = DrawerState(initialValue = DrawerValue.Closed)

        // COMENTARIO.
        if (appUiState.value.drawerState.isClosed) {
            drawerState = DrawerState(initialValue = DrawerValue.Open)
        }

        // COMENTARIO.
        _appUiState.update { currentState ->
            currentState.copy(drawerState = drawerState)
        }
    }
}