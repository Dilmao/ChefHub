package com.example.chefhub.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chefhub.db.ChefhubDB
import com.example.chefhub.db.SettingOption
import com.example.chefhub.db.data.Favorites
import com.example.chefhub.db.data.Follows
import com.example.chefhub.db.data.Recipes
import com.example.chefhub.db.data.Users
import com.example.chefhub.db.repository.RecipesRepository
import com.example.chefhub.screens.components.showMessage
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.exp

class AppViewModel(private val database: ChefhubDB): ViewModel() {
    private val _appUiState = MutableStateFlow(AppUiState())
    val appUiState: StateFlow<AppUiState> = _appUiState.asStateFlow()

    /** Funciones generales **/
    fun restartBoolean() {
        // Se reinician las variables de tipo boolean.
        _appUiState.update { currentState ->
            currentState.copy(showMessage = false)
        }
    }


    /** Funciones User **/
    fun <T> onUserChanged(
        newValue: T,
        valueName: String,
    ) {
        // Se obtiene el estado actual de la UI y se prepara el estado actualizado.
        val currentState = appUiState.value
        var updatedState = currentState

        // Se actualiza el estado dependiendo del campo que se esté modificando.
        when (valueName) {
            "userName" -> if (newValue is String) updatedState = currentState.copy(user = currentState.user.copy(userName = newValue))
            "email" -> if (newValue is String) updatedState = currentState.copy(user = currentState.user.copy(email = newValue))
            "password" -> if (newValue is String) updatedState = currentState.copy(user = currentState.user.copy(password = newValue))
            "profilePicture" -> if (newValue is String) updatedState = currentState.copy(user = currentState.user.copy(profilePicture = newValue))
            "bio" -> if (newValue is String) updatedState = currentState.copy(user = currentState.user.copy(bio = newValue))
            "isActive" -> if (newValue is Boolean) updatedState = currentState.copy(user = currentState.user.copy(isActive = newValue))
        }

        // Se actualiza el estado de la UI.
        _appUiState.update { updatedState }
    }

    fun resetUserValues() {
        // Se reiniciar los datos del usuario.
        val user = Users()
        val favorites: MutableList<Recipes> = arrayListOf()
        _appUiState.update { currentState ->
            currentState.copy(
                user = user,
                favorites = favorites
            )
        }
    }


    /** Funciones Recipe **/
    fun <T> onRecipeChanged(
        newValue: T,
        valueName: String,
    ) {
        // Se obtiene el estado actual de la UI y se prepara el estado actualizado.
        val currentState = appUiState.value
        var updatedState = currentState

        // Se actualiza el estado dependiendo del campo que se esté modificando.
        when (valueName) {
            "title" -> if (newValue is String) updatedState = currentState.copy(recipe = currentState.recipe.copy(title = newValue))
            "description" -> if (newValue is String) updatedState = currentState.copy(recipe = currentState.recipe.copy(description = newValue))
            "imageUrl" -> if (newValue is String) updatedState = currentState.copy(recipe = currentState.recipe.copy(imageUrl = newValue))
            "prepHour" -> if (newValue is Int) updatedState = currentState.copy(prepHour = newValue)
            "prepMin" -> if (newValue is Int) updatedState = currentState.copy(prepMin = newValue)
            "cookHour" -> if (newValue is Int) updatedState = currentState.copy(cookHour = newValue)
            "cookMin" -> if (newValue is Int) updatedState = currentState.copy(cookMin = newValue)
            "servings" -> if (newValue is Int) updatedState = currentState.copy(servings = newValue)
        }

        // Se actualiza el estado de la UI.
        _appUiState.update { updatedState }
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

    fun resetRecipeValues() {
        // Se reiniciar los datos del usuario.
        val recipe = Recipes()
        _appUiState.update { currentState ->
            currentState.copy(
                recipe = recipe,
                prepHour = 0,
                prepMin = 0,
                cookHour = 0,
                cookMin = 0,
                servings = 0
            )
        }
    }


    /** Funciones Login **/
    fun checkLogin(callback: (Int) -> Unit) {
        val auth: FirebaseAuth = Firebase.auth
        val email = appUiState.value.user.email
        val password = appUiState.value.user.password

        // Validar si los campos están vacíos.
        viewModelScope.launch {
            if (email.isEmpty() || password.isEmpty()) {
                callback(1) // 1: Campos están vacíos.
            } else if (database.usersDao.getUserByEmail(email) == null) {
                callback(2) // 2: Usuario no encontrado en SQLite.
            } else {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        viewModelScope.launch {
                            try {
                                // Sincronizar datos del usuario desde SQLite.
                                val user = database.usersDao.getUserByEmail(email)
                                if (user != null) {
                                    // Actualizar el estado de la UI.
                                    _appUiState.update { currentState ->
                                        currentState.copy(user = user)
                                    }
                                    callback(0) // 0: Inicio de sesión exitoso.
                                }
                            } catch (e: Exception) {
                                callback(4) // 5: Error inesperado.
                            }
                        }
                    } else {
                        when (val exception = task.exception) {
                            is FirebaseAuthInvalidUserException -> callback(2) // 2: Correo no encotrado en Firebase.
                            is FirebaseAuthInvalidCredentialsException -> callback(3) // 3: Contraseña incorrecta.
                            else -> callback(4) // 5: Error inesperado.
                        }
                    }
                }
            }
        }
    }


    /** Funciones Register **/
    fun checkRegister(confirmPassword: String, callback: (Int) -> Unit) {
        val auth = Firebase.auth
        val newUserName = appUiState.value.user.userName
        val newEmail = appUiState.value.user.email
        val newPassword = appUiState.value.user.password

        // Se inicia una operación en segundo plano.
        viewModelScope.launch {
            if (newEmail.isEmpty() || newPassword.isEmpty() || newUserName.isEmpty()) {
                callback(1) // 1: Campos vacíos.
            } else if(!newEmail.contains("@")) {
                callback(2) // 2: El correo no contiene '@'.
            } else if(newPassword.length < 10 || newPassword.length > 30) {
                callback(3) // 3: La contraseña tiene un tamaño inválido.
            } else if(!newPassword.equals(confirmPassword)) {
                callback(4) // 4: Contraseña y confirmación no coindicen.
            } else if(database.usersDao.getUserByEmail(newEmail) != null) {
                callback(5) // 5: El correo ya existe en SQLite.
            } else if(database.usersDao.getUserByUserName(newUserName) != null) {
                callback(6) // 6: El nombre ya existe en SQLite.
            } else {
                // Se registra el usuario en Firebase.
                auth.createUserWithEmailAndPassword(newEmail, newPassword).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Se crea un objeto 'Users' con la información del nuevo usuario.
                        val newUser = Users(
                            userName = newUserName,
                            email = newEmail,
                            password = newPassword
                        )

                        viewModelScope.launch {
                            try {
                                // Se inserta el nuevo usuario en SQLite.
                                database.usersDao.insertUser(newUser)
                                _appUiState.update { currentState ->
                                    currentState.copy(user = newUser)
                                }
                                callback(0) // 0: Registro exitoso.
                            } catch (e: Exception) {
                                callback(7) // 7: Error inesperado al guardar el usuario en SQLite.
                            }
                        }
                    } else {
                        // Correo electrónico ya en uso en Firebase.
                        if (task.exception is FirebaseAuthUserCollisionException) {
                            callback(5) // 5: El correo ya existe en Firebase.
                        } else {
                            callback(7) // 7: Error inesperado al registrar el usuario en Firebase.
                        }
                    }
                }
            }
        }
    }

    /** Funciones Password Recovery **/
    fun recoverPassword(callback: (Int) -> Unit) {
        val auth = Firebase.auth
        val email = appUiState.value.user.email

        viewModelScope.launch {
            if (email.isEmpty()) {
                callback(1) // 1: Campo 'correo' vacío.
            } else if (database.usersDao.getUserByEmail(email) != null) {
                callback(2) // 2: Usuario no encontrado en SQLite.
            } else {
                // Se envia un correo para restablecer la contraseña.
                auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        callback(0) // 0: Correo enviado correctamente.
                    } else {
                        when (val exception = task.exception) {
                            is FirebaseAuthException -> {
                                if (exception.errorCode == "ERROR_USER_NOT_FOUND") {
                                    callback(2) // 2: Usuario no encontrado en Firebase.
                                } else {
                                    callback(3) // 3: Error inesperado al enviar el correo.
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /** Funciones Main **/
    fun loadMain() {
        viewModelScope.launch {
            val user = appUiState.value.user

            // Obtener los usuarios seguidos y transformar el Flow en una lista de IDs.
            val followedUsers = database.followsDao.getFollowingForUser(user.userId).firstOrNull()
            val followedUsersIds = followedUsers?.map { it.userId } ?: emptyList()

            // Obtener las recetas de los usuarios seguidos y convertirlas en MutableList.
            val recipesList = database.recipesDao.getRecipesByFollowedUsers(followedUsersIds).firstOrNull() ?: emptyList()

            // Actualizar el estado de la UI.
            _appUiState.update { currentState ->
                currentState.copy(recipes = recipesList.toMutableList())
            }
        }
    }

    /** Funciones Search **/
    fun onSearchChanged(search: String) {
        // COMENTARIO.
        _appUiState.update {currentState ->
            currentState.copy(search = search)
        }
    }

    fun search(searchType: String) {
        // COMENTARIO.
        viewModelScope.launch {
            when(searchType.lowercase()) {
                "users" -> {
                    val users = database.usersDao.getUsers().firstOrNull() ?: emptyList()
                    _appUiState.update { currentState ->
                        currentState.copy(users = ArrayList(users.toCollection(mutableListOf())))
                    }
                }
                "recipes" -> {
                    val recipes = database.recipesDao.getRecipes().firstOrNull() ?: emptyList()
                    _appUiState.update { currentState ->
                        currentState.copy(recipes = ArrayList(recipes.toCollection(mutableListOf())))
                    }
                }
                else -> { println("Tipo desconocido: $searchType.") }
            }
        }
    }

    /** Funciones Add Recipe **/
    fun saveRecipe(
        context: Context,
        action: String,
        callback: (Boolean) -> Unit
    ) { // TODO: Asegurar que los campos obligatorios esta rellenados.
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

        if (recipe.title.isEmpty()) {
            showMessage(context, "Título vacío")
            callback(false)
        } else {
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
    }


    /** Funciones AccountScreen **/
    fun loadAccount() {
        val userId = appUiState.value.user.userId

        viewModelScope.launch {
            val recipes = database.recipesDao.getRecipesByUser(userId).firstOrNull() ?: emptyList()
            val followers = database.followsDao.getFollowersForUser(userId).firstOrNull() ?: emptyList()
            val following = database.followsDao.getFollowingForUser(userId).firstOrNull() ?: emptyList()

            _appUiState.update { currentState ->
                currentState.copy(
                    recipes = recipes.toCollection(mutableListOf()),
                    followers = followers.toCollection(mutableListOf()),
                    following = following.toCollection(mutableListOf())
                )
            }
        }
    }

    fun changeView(
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
    fun onChangeSettingsScreen(newList: List<SettingOption>) {
        _appUiState.update { currentState ->
            currentState.copy(settingsOptions = newList) // TODO: Arreglar
        }
    }

    fun logOut() {
        val auth: FirebaseAuth = Firebase.auth
        auth.signOut()

        _appUiState.update { currentState ->
            currentState.copy(
                user = Users(),
                viewedUser = Users(),
                users = arrayListOf(),
                followers = arrayListOf(),
                following = arrayListOf(),
                recipe = Recipes(),
                recipes = arrayListOf(),
                favorites = arrayListOf(),
            )
        }
    }

    fun deleteAccount() {
        // COMENTARIO.
        val auth: FirebaseAuth = Firebase.auth
        val user = appUiState.value.user

        // COMENTARIO.
        viewModelScope.launch {
            auth.signOut()
            database.usersDao.deleteUser(user)

            _appUiState.update { currentState ->
                currentState.copy(
                    user = Users(),
                    viewedUser = Users(),
                    users = arrayListOf(),
                    followers = arrayListOf(),
                    following = arrayListOf(),
                    recipe = Recipes(),
                    recipes = arrayListOf(),
                    favorites = arrayListOf(),
                )
            }
        }
    }

    /** Funciones Database **/
    fun onSelectUser(user: Users) {
        viewModelScope.launch {
            // Obtener las recetas asociadas al usuario como una lista
            val recipesList = database.recipesDao.getRecipesByUser(user.userId).firstOrNull() ?: emptyList()
            val followedUsers = database.followsDao.getFollowingForUser(appUiState.value.user.userId).firstOrNull() ?: emptyList()

            // Actualizar el estado de la UI con la lista obtenida
            _appUiState.update { currentState ->
                currentState.copy(
                    viewedUser = user,
                    recipes = ArrayList(recipesList.toCollection(mutableListOf())),
                    following = ArrayList(followedUsers.toCollection(mutableListOf())),
                )
            }
        }
    }

    fun onFollowUser(userFollowed: Boolean) {
        val followerId = appUiState.value.user.userId
        val followingId = appUiState.value.viewedUser.userId
        val follows = Follows(followerId, followingId)

        viewModelScope.launch {
            if (userFollowed) {
                // Elimina la relación de la base de datos.
                database.followsDao.deleteFollower(follows)
                println("Delete follows: $followerId; $followingId; $follows")
            } else {
                // Inserta la relación en la base de datos.
                database.followsDao.insertFollower(follows)
                println("Insert follows: $followerId; $followingId; $follows")
            }

            val followsTable = database.followsDao.getFollowingForUser(followerId).firstOrNull() ?: emptyList()
            println("Following for user $followerId: $followsTable")

            // Se obtiene la lista actualizada de usuarios seguidos.
            val updatedFollowedUsers = database.followsDao.getFollowingForUser(followerId).firstOrNull() ?: emptyList()

            // Actualizar el estado de la UI con la lista actualizada.
            _appUiState.update { currentState ->
                currentState.copy(following = updatedFollowedUsers.toMutableList())
            }
        }
    }


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

    fun onChangeFavorite(recipe: Recipes, action: String, context: Context) {
        val favorite = Favorites(
            userId = appUiState.value.user.userId,
            recipeId = recipe.recipeId
        )

        viewModelScope.launch {
            when (action.lowercase()) {
                "save" -> {
                    database.favoritesDao.insertFavorite(favorite)
                    showMessage(context, "Receta añadida a favoritos.")
                }
                "delete" -> {
                    database.favoritesDao.deleteFavorite(favorite)
                    showMessage(context, "Receta eliminada de favoritos.")
                }
                else -> {
                    showMessage(context, "Acción desconocida: $action.")
                }
            }
            val favoriteRecipes = database.favoritesDao.getFavoritesForUser(appUiState.value.user.userId).first().toMutableList()
            _appUiState.update { currentState ->
                currentState.copy(favorites = favoriteRecipes)
            }
        }
    }
}