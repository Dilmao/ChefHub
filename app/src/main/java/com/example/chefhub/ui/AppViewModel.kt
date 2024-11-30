package com.example.chefhub.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chefhub.db.ChefhubDB
import com.example.chefhub.db.SettingOption
import com.example.chefhub.db.data.Recipes
import com.example.chefhub.db.data.Users
import com.example.chefhub.db.repository.RecipesRepository
import com.example.chefhub.db.repository.UsersRepository
import com.example.chefhub.screens.components.saveCredentials
import com.example.chefhub.screens.components.showMessage
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel(private val database: ChefhubDB): ViewModel() {
    private val _appUiState = MutableStateFlow(AppUiState())
    val appUiState: StateFlow<AppUiState> = _appUiState.asStateFlow()

    /** Funciones generales **/
    // TODO: Pensar en hacer una funcion changeUI (como el changeRegister) en vez de tener varias funciones change...
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
            currentState.copy(
                user = currentState.user.copy(
                    email = email,
                    password = password
                )
            )
        }
    }

    fun checkLogin(
        context: Context,
        callback: (Boolean) -> Unit
    ) {
        val auth: FirebaseAuth = Firebase.auth

        // Obtener correo y contraseña del estado de la UI.
        val email = appUiState.value.user.email
        val password = appUiState.value.user.password

        // Se intenta iniciar sesión con Firebase.
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Se guardan las credenciales de manera segura.
                saveCredentials(context, email, password)

                // Se sincronizan los datos del usuario desde la base de datos local.
                viewModelScope.launch {
                    val userRepository = UsersRepository(database.usersDao)

                    try {
                        val user = userRepository.getUserByEmail(email)
                        if (user != null) {
                            // Se guarda el ID del usuario.
                            _appUiState.update { currentState ->
                                currentState.copy(user = user)
                            }
                        } else {
                            // Mensaje de error en caso de que el usuario no este en la base de datos local.
                            showMessage(context, "Usuario autenticado, pero no encontrado localmente.")
                        }
                    } catch (e: Exception) {
                        showMessage(context, "Error al sincronizar datos del usuario.")
                    }
                }

                // Mensaje de exito.
                showMessage(context, "Inicio de sesión exitoso.")
                callback(true)
            } else {
                // Manejo de errores de Firebase
                val errorMessage = when (task.exception) {
                    is FirebaseAuthInvalidUserException -> "El correo no está registrado."
                    is FirebaseAuthInvalidCredentialsException -> "La contraseña es incorrecta."
                    else -> "Error al iniciar sesión: ${task.exception?.message}"
                }
                showMessage(context = context, mensaje = errorMessage)
                callback(false)
            }
        }
    }

    /** Funciones Register **/
    fun onRegisterChanged(
        newValue: String,
        valueName: String
    ) {
        // Se obtiene el estado actual de la UI.
        val currentState = appUiState.value

        // Se actualiza el estado dependiendo del campo que se esté modificando dentro del usuario.
        val updatedState = when (valueName) {
            "userName" -> currentState.copy(user = currentState.user.copy(userName = newValue))
            "email" -> currentState.copy(user = currentState.user.copy(email = newValue))
            "password" -> currentState.copy(user = currentState.user.copy(password = newValue))
            // En caso de que el valor modificado no esté entre las opciones, se muestra un mensaje de error inesperado.
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
        val auth = Firebase.auth

        // Obtener datos del estado de la UI.
        val newUserName = appUiState.value.user.userName
        val newEmail = appUiState.value.user.email
        val newPassword = appUiState.value.user.password
        val userRepository = UsersRepository(database.usersDao)

        // Comprueba si el nombre de usuario ya existe.
        viewModelScope.launch {
            val userExist = userRepository.getUserByUserName(newUserName) != null
            if (userExist) {
                showMessage(context, "El nombre de usuario ya está en uso.")
                callback(false)
                return@launch
            }

            // Registrar el usuario en Firebase.
            auth.createUserWithEmailAndPassword(newEmail, newPassword).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Se crea el objeto Users para guardarlo en la base de datos local.
                    val newUser = Users(
                        userName = newUserName,
                        email = newEmail,
                        password = newPassword
                    )

                    // Se inserta el nuevo usuario en la base de datos local.
                    viewModelScope.launch {
                        try {
                            userRepository.insertUser(newUser)
                            _appUiState.update { currentState ->
                                currentState.copy(user = newUser)
                            }
                            showMessage(context, "Nuevo usuario creado con éxito.")
                            callback(true)
                        } catch (e: Exception) {
                            showMessage(context, "Error al guardar el usuario en la base de datos local.")
                            callback(false)
                        }
                    }
                } else {
                    // Mensaje de error.
                    val errorMessage = when (task.exception) {
                        is FirebaseAuthUserCollisionException -> "El correo electrónico ya está registrado."
                        is FirebaseAuthWeakPasswordException -> "La contraseña es demasiado débil."
                        else -> "Error al registrar el usuario: ${task.exception?.message}."
                    }
                    showMessage(context, errorMessage)
                    callback(false)
                }
            }
        }
    }

    /** Funciones Password Recovery **/
    fun onRecoveryChanged(
        recoveryEmail: String
    ) {
        // Se actualiza el estado de la página PasswordRecoveryScreen.
        _appUiState.update { currentState ->
            currentState.copy(user = currentState.user.copy(email = recoveryEmail))
        }
    }

    fun recoverPassword( // TODO: Si el usuario cambia de contraseña, esta se modifica en Firebase, pero no en Room.
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
                    errorMessage =
                        "Se ha enviado un correo electrónico a la dirección: \"${appUiState.value.user.email}\".\n Siga las instrucciones en el correo para cambiar la contraseña."
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
                        }

                        else -> {
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
            currentState.copy(recipe = currentState.recipe.copy(title = recipeTitle))
        }
    }

    fun onMutableListChanged(
        newValue: String,
        index: Int,
        list: ArrayList<String>,
        listName: String
    ) {
        // Crear una copia de la lista en forma de ArrayList.
        val updatedList = ArrayList(list)
        updatedList[index] = newValue

        // Actualizar el estado de la UI con la lista modificada.
        _appUiState.value = when (listName) {
            "Ingredient" -> appUiState.value.copy(
                recipe = appUiState.value.recipe.copy(ingredients = updatedList)
            )
            "Instruction" -> appUiState.value.copy(
                recipe = appUiState.value.recipe.copy(instructions = updatedList)
            )
            else -> throw IllegalArgumentException("El nombre de la lista '$listName' no es válido.")
        }
    }


    fun onMutableListAddElement(
        list: ArrayList<String>,
        listName: String
    ) {
        // Crear una copia de la lista en forma de ArrayList.
        val updatedList = ArrayList(list)
        updatedList.add("")

        // COMENTARIO.
        _appUiState.value = when (listName) {
            "Ingredient" -> appUiState.value.copy(
                recipe = appUiState.value.recipe.copy(ingredients = updatedList)
            )
            "Instruction" -> appUiState.value.copy(
                recipe = appUiState.value.recipe.copy(instructions = updatedList)
            )
            else -> throw IllegalArgumentException("El nombre de la lista '$listName' no es válido.")
        }
    }

    fun onRecipeIntChanged(
        newValue: Int,
        valueName: String
    ) {
        // Se obtiene el estado actual de la UI.
        val currentState = appUiState.value

        // Se actualiza el estado dependiendo del campo que se esté modificando dentro del usuario.
        val updatedState = when (valueName) {
            "prepHour" -> currentState.copy(prepHour = newValue)
            "prepMin" -> currentState.copy(prepMin = newValue)
            "cookHour" -> currentState.copy(cookHour = newValue)
            "cookMin" -> currentState.copy(cookMin = newValue)
            "servings" -> currentState.copy(servings = newValue)
            else -> currentState.copy(
                messageText = "Error inesperado en AddRecipeScreen ($valueName)",
                showMessage = true
            )
        }

        // Se actualiza el estado de la página RegisterScreen.
        _appUiState.update { updatedState }
    }

    fun onSaveRecipe(
        context: Context,
        action: String
    ) {
        val uiState = appUiState.value
        val recipesRepository = RecipesRepository(database.recipesDao)

        val recipe = Recipes(
            userId = uiState.user.userId,
            title = uiState.recipe.title,
            description = uiState.recipe.description,
            ingredients = uiState.recipe.ingredients,
            instructions = uiState.recipe.instructions,
            imageUrl = uiState.recipe.imageUrl,
            prepTime = uiState.prepHour * 60 + uiState.prepMin,
            cookTime = uiState.cookHour * 60 + uiState.cookMin,
            servings = uiState.servings
        )

        viewModelScope.launch {
            try {
                when (action.lowercase()) {
                    "create" -> {
                        recipesRepository.insertRecipe(recipe)
                        showMessage(context, "Receta creada con éxito.")
                    }
                    "save" -> {
                        recipe.recipeId = uiState.recipe.recipeId
                        recipesRepository.updateRecipe(recipe)
                        showMessage(context, "Receta actualizada con éxito.")
                    }
                    else -> {
                        showMessage(context, "Acción desconocida: $action.")
                    }
                }
            } catch (e: Exception) {
                val errorMsg = "Error al ${if (action == "create") "crear" else "guardar"} la receta: ${e.message}"
                showMessage(context, errorMsg)
            }
        }
    }


    /** Funciones AccountScreen **/
    fun onChangeView(
        view: String,
    ) {
        viewModelScope.launch {
            val updatedList: ArrayList<Recipes> = when (view) {
                "recipes" -> {
                    // Recoger las recetas del usuario como una lista y convertirlas a ArrayList
                    val recipes = database.recipesDao.getRecipesByUser(appUiState.value.user.userId).firstOrNull() ?: emptyList()
                    ArrayList(recipes.toCollection(mutableListOf()))
                }
                "saved" -> {
                    // Recoger las recetas favoritas del usuario como una lista y convertirlas a ArrayList
                    val favorites = database.favoritesDao.getFavoritesForUser(appUiState.value.user.userId).firstOrNull() ?: emptyList()
                    ArrayList(favorites.toCollection(mutableListOf()))
                }
                "something" -> {
                    // Devolver una lista vacía
                    arrayListOf()
                }
                else -> {
                    // Manejo de casos no esperados
                    arrayListOf()
                }
            }

            // Actualizar el estado de la UI
            _appUiState.update { currentState ->
                currentState.copy(recipes = updatedList)
            }
        }
    }


    /** Funciones SettingsScreen **/
    fun resetUserValues() {
        // Se reiniciar los datos del usuario.
        val user = Users()
        _appUiState.update { currentState ->
            currentState.copy(
                user = user,
            )
        }
    }

    fun resetRecipeValues() {
        // Se reiniciar los datos del usuario.
        val recipe = Recipes()
        _appUiState.update { currentState ->
            currentState.copy(
                recipe = recipe,
            )
        }
    }

    fun onChangeSettingsScreen(newList: List<SettingOption>) {
        _appUiState.update { currentState ->
            currentState.copy(settingsOptions = newList) // TODO: Arreglar
        }
    }


    /** Funciones Database **/
    fun onSelectRecipe(recipe: Recipes) {
        val prepHour = recipe.prepTime?.div(60) ?: 0
        val prepMin = recipe.prepTime?.rem(60) ?: 0
        val cookHour = recipe.cookTime?.div(60) ?: 0
        val cookMin = recipe.cookTime?.rem(60) ?: 0
        val servings = recipe.servings ?: 0

        _appUiState.update { currentState ->
            currentState.copy(
                recipe = recipe,
                prepHour = prepHour,
                prepMin = prepMin,
                cookHour = cookHour,
                cookMin = cookMin,
                servings = servings
            )
        }
    }

    fun onDeleteRecipe(recipe: Recipes) {
        viewModelScope.launch {
            database.recipesDao.deleteRecipe(recipes = recipe)
        }
    }
}