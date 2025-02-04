package com.example.chefhub.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chefhub.db.ChefhubDB
import com.example.chefhub.db.SettingOption
import com.example.chefhub.db.data.Favorites
import com.example.chefhub.db.data.Follows
import com.example.chefhub.db.data.Ratings
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

open class AppViewModel(private val database: ChefhubDB): ViewModel() {
    private val _appUiState = MutableStateFlow(AppUiState())
    val appUiState: StateFlow<AppUiState> = _appUiState.asStateFlow()

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
            "all" -> if (newValue is Users) {
                updatedState = currentState.copy(user = newValue)

                // Se actualiza la base de datos con el nuevo valor.
                updateUserInDatabase(newValue)
            }
        }

        // Se actualiza el estado de la UI.
        _appUiState.update { updatedState }
    }

    private fun updateUserInDatabase(user: Users) {
        viewModelScope.launch {
            database.usersDao.updateUser(user)
        }
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
            "dificulty" -> if (newValue is String) updatedState = currentState.copy(recipe = currentState.recipe.copy(dificulty = newValue))
            "category" -> if (newValue is String) updatedState = currentState.copy(recipe = currentState.recipe.copy(categoryName = newValue))
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
        var tries = appUiState.value.tries

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
                        when (task.exception) {
                            is FirebaseAuthInvalidUserException -> callback(2) // 2: Correo no encotrado en Firebase.
                            is FirebaseAuthInvalidCredentialsException -> {
                                callback(3)
                                tries--
                            } // 3: Contraseña incorrecta.
                            else -> callback(4) // 5: Error inesperado.
                        }

                        _appUiState.update { currentState ->
                            currentState.copy(tries = tries)
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
            } else if (!newPassword.matches(Regex("^(?=.*[a-z])(?=.*[A-Z]).+$"))) {
                callback(4) // 4: La contraseña debe contener al menos una letra mayúscula y una letra minúscula.
            } else if(!newPassword.equals(confirmPassword)) {
                callback(5) // 5: Contraseña y confirmación no coindicen.
            } else if(database.usersDao.getUserByEmail(newEmail) != null) {
                callback(6) // 6: El correo ya existe en SQLite.
            } else if(database.usersDao.getUserByUserName(newUserName) != null) {
                callback(7) // 7: El nombre ya existe en SQLite.
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
                                callback(8) // 8: Error inesperado al guardar el usuario en SQLite.
                            }
                        }
                    } else {
                        // Correo electrónico ya en uso en Firebase.
                        if (task.exception is FirebaseAuthUserCollisionException) {
                            callback(6) // 6: El correo ya existe en Firebase.
                        } else {
                            callback(8) // 8: Error inesperado al registrar el usuario en Firebase.
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
            } else if (database.usersDao.getUserByEmail(email) == null) {
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

            // Obtener todas las categorías.
            val categories = database.categoriesDao.getCategories().firstOrNull() ?: emptyList()

            // Actualizar el estado de la UI.
            _appUiState.update { currentState ->
                currentState.copy(
                    recipes = recipesList.toMutableList(),
                    categories = categories.toMutableList()
                )
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
        viewModelScope.launch {
            when (searchType.lowercase()) {
                "users" -> {
                    // Busca usuarios cuyo nombre de usuario contenga 'searchItem'
                    val users = database.usersDao.searchUsersByUserName("%${appUiState.value.search}%").firstOrNull() ?: emptyList()
                    _appUiState.update { currentState ->
                        currentState.copy(users = ArrayList(users.toCollection(mutableListOf())))
                    }
                }
                "recipes" -> {
                    // Busca recetas cuyo título contenga 'searchItem'
                    val recipes = database.recipesDao.searchRecipesByTitle("%${appUiState.value.search}%").firstOrNull() ?: emptyList()
                    _appUiState.update { currentState ->
                        currentState.copy(recipes = ArrayList(recipes.toCollection(mutableListOf())))
                    }
                }
                "categories" -> {
                    // Busca categorías cuyo nombre contenga 'searchItem'
                    val categories = database.categoriesDao.searchCategoriesByName("%${appUiState.value.search}%").firstOrNull() ?: emptyList()
                    _appUiState.update { currentState ->
                        currentState.copy(categories = ArrayList(categories.toCollection(mutableListOf())))
                    }
                }
                else -> {
                    println("Tipo desconocido: $searchType.")
                }
            }
        }
    }


    /** Funciones Add Recipe **/
    fun saveRecipe(
        action: String,
        callback: (Int) -> Unit
    ) { // TODO: Asegurar que los campos obligatorios esta rellenados.
        val uiState = appUiState.value

        val recipe = Recipes(
            userId = uiState.user.userId,
            categoryName = uiState.recipe.categoryName,
            title = uiState.recipe.title,
            description = uiState.recipe.description,
            dificulty = uiState.recipe.dificulty,
            ingredients = uiState.recipe.ingredients,
            instructions = uiState.recipe.instructions,
            imageUrl = uiState.recipe.imageUrl,
            prepTime = uiState.prepHour * 60 + uiState.prepMin,
            cookTime = uiState.cookHour * 60 + uiState.cookMin,
            servings = uiState.servings
        )

        if (recipe.title.isEmpty()) {
            callback(1)
        } else if (recipe.title.length < 3 || recipe.title.length > 50) {
            callback(2)
        } else if (!recipe.imageUrl.isNullOrBlank() && !recipe.imageUrl.endsWith(".png") && !recipe.imageUrl.endsWith(".jpg")) {
            callback(3)
        } else if ((recipe.prepTime ?: 0) < 0 || (recipe.cookTime ?: 0) < 0) {
            callback(0)
        } else {
            viewModelScope.launch {
                try {
                    when (action.lowercase()) {
                        "create" -> {
                            database.recipesDao.insertRecipe(recipe)
                            callback(0)
                        }
                        "save" -> {
                            recipe.recipeId = uiState.recipe.recipeId
                            callback(0)
                        }
                        else -> {
                            callback(5)
                        }
                    }
                } catch (e: Exception) {
                    callback(5)
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
        userId: Int
    ) {
        viewModelScope.launch {
            val updatedList: ArrayList<Recipes> = when (view) {
                "recipes" -> {
                    // Recoger las recetas del usuario como una lista y convertirlas a ArrayList
                    val recipes = database.recipesDao.getRecipesByUser(userId).firstOrNull() ?: emptyList()
                    ArrayList(recipes.toCollection(mutableListOf()))
                }
                "saved" -> {
                    // Recoger las recetas favoritas del usuario como una lista y convertirlas a ArrayList
                    val favorites = database.favoritesDao.getFavoritesForUser(userId).firstOrNull() ?: emptyList()
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
            currentState.copy(
                settingsOptions = newList
            )
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
        val auth: FirebaseAuth = Firebase.auth
        val user = appUiState.value.user

        try {
            auth.currentUser?.delete()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    viewModelScope.launch {
                        try {
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
                        } catch (dbException: Exception) {
                            println("Error.")
                        }
                    }
                }
            }
        } catch (exception: Exception) {
            println("Error.")
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
        viewModelScope.launch {
            val prepHour = recipe.prepTime?.div(60) ?: 0
            val prepMin = recipe.prepTime?.rem(60) ?: 0
            val cookHour = recipe.cookTime?.div(60) ?: 0
            val cookMin = recipe.cookTime?.rem(60) ?: 0
            val servings = recipe.servings ?: 0
            val ratings = database.ratingsDao.getRatingsForRecipe(recipe.recipeId).firstOrNull() ?: emptyList()

            _appUiState.update { currentState ->
                currentState.copy(
                    recipe = recipe,
                    prepHour = prepHour,
                    prepMin = prepMin,
                    cookHour = cookHour,
                    cookMin = cookMin,
                    servings = servings,
                    ratings = ratings.toMutableList(),
                )
            }
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

    fun onRateRecipe(selectedRating: Int) {
        viewModelScope.launch {
            val recipeId = appUiState.value.recipe.recipeId
            val userId = appUiState.value.user.userId

            // Se verifica si ya existe una valoración para este usuario y receta.
            val existingRating = database.ratingsDao.getRatingForUserAndRecipe(userId, recipeId)

            if (existingRating != null) {
                // Si existe, se actualiza la valoración.
                val updatedRating = existingRating.copy(rating = selectedRating)

                database.ratingsDao.updateRating(updatedRating)

                // COMENTARIO.
                val updatedList = ArrayList(appUiState.value.ratings).apply {
                    val index = indexOfFirst { it.ratingId == existingRating.ratingId }
                    if (index != -1) {
                        this[index] = updatedRating
                    }
                }

                _appUiState.update { currentState ->
                    currentState.copy(ratings = updatedList)
                }
            } else {
                // Si no existe, insertamos una nueva valoración.
                val newRating = Ratings(
                    userId = userId,
                    recipeId = recipeId,
                    rating = selectedRating
                )

                database.ratingsDao.insertRating(newRating)

                val updatedList = ArrayList(appUiState.value.ratings)
                updatedList.add(newRating)

                _appUiState.update { currentState ->
                    currentState.copy(ratings = updatedList)
                }
            }
        }
    }
}