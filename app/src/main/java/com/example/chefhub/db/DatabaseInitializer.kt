package com.example.chefhub.db

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.firstOrNull
import com.example.chefhub.db.data.Categories
import com.example.chefhub.db.data.Recipes
import com.example.chefhub.db.data.Users

object DatabaseInitializer {

    // Inicializa los datos de la base de datos, como las categorías predefinidas.
    fun initialize(database: ChefhubDB) {
        // Usamos un CoroutineScope para realizar la operación de forma asíncrona.
        CoroutineScope(Dispatchers.IO).launch {
            addDefaultCategories(database)
            addDefaultUsers(database)
            addDefaultRecipes(database)
        }
    }

    //Agrega las categorías por defecto si no existen en la base de datos.
    private suspend fun addDefaultCategories(database: ChefhubDB) {
        val categoriesDao = database.categoriesDao
        val existingCategories = categoriesDao.getCategories().firstOrNull()

        // Solo agregar si no existen categorías.
        if (existingCategories.isNullOrEmpty()) {
            val categories = listOf(
                Categories(categoryName = "Alimentación infantil"),
                Categories(categoryName = "Aperitivos y tapas"),
                Categories(categoryName = "Sopas y cremas"),
                Categories(categoryName = "Arroces y pastas"),
                Categories(categoryName = "Potajes y platos de cuchara"),
                Categories(categoryName = "Verduras y hortalizas"),
                Categories(categoryName = "Carnes y aves"),
                Categories(categoryName = "Pescados y mariscos"),
                Categories(categoryName = "Dulces y postres"),
                Categories(categoryName = "Bebidas y refrescos"),
                Categories(categoryName = "Masas y repostería"),
                Categories(categoryName = "Salsas"),
                Categories(categoryName = "Guarniciones y acompañamientos"),
                Categories(categoryName = "Básicas"),
                Categories(categoryName = "Dietas trituradas"),
                Categories(categoryName = "Navidad"),
            )

            for (category in categories) {
                categoriesDao.insertCategory(category)
            }
        }
    }

    private suspend fun addDefaultUsers(database: ChefhubDB) {
        val usersDao = database.usersDao
        val existingUsers = usersDao.getUsers().firstOrNull()

        if (existingUsers.isNullOrEmpty()) {
            val users = listOf(
                Users(
                    userName = "Dilamo22",
                    email = "200066@iessanalberto.com",
                    password = "Dilamo0365",
                    profilePicture = null,
                    bio = "He perdido",
                    isActive = true
                ),
                Users(
                    userName = "Dilamo.121",
                    email = "diegolacasa04@gmail.com",
                    password = "Dilamo0365",
                    profilePicture = null,
                    bio = "He perdido",
                    isActive = true
                )
            )

            for (user in users) {
                usersDao.insertUser(user)
            }
        }
    }

    private suspend fun addDefaultRecipes(database: ChefhubDB) {
        val recipesDao = database.recipesDao
        val existingRecipes = recipesDao.getRecipes().firstOrNull()

        if (existingRecipes.isNullOrEmpty()) {
            val recipes = listOf(
                Recipes(
                    userId = 1,
                    categoryName = "Alimentación infantil",
                    title = "Recipe Number 1",
                    description = "Primera receta",
                    dificulty = "Muy fácil",
                    ingredients = arrayListOf("Primer ingrediente"),
                    instructions = arrayListOf("Primera instrucción"),
                    imageUrl = null,
                    prepTime = 70, // 1 hora y 10 minutos
                    cookTime = 10, // 10 minutos
                    servings = 1
                ),
                Recipes(
                    userId = 1,
                    categoryName = "Aperitivos y tapas",
                    title = "Recipe Number 2",
                    description = "Segunda receta",
                    dificulty = "Fácil",
                    ingredients = arrayListOf("Primer ingrediente", "Segundo ingrediente"),
                    instructions = arrayListOf("Primera instrucción", "Segunda instrucción"),
                    imageUrl = null,
                    prepTime = 140, // 2 horas y 20 minutos
                    cookTime = 20, // 20 minutos
                    servings = 2
                ),
                Recipes(
                    userId = 1,
                    categoryName = "Sopas y cremas",
                    title = "Recipe Number 3",
                    description = "Tercera receta",
                    dificulty = "Medio",
                    ingredients = arrayListOf("Primer ingrediente", "Segundo ingrediente", "Tercer ingrediente"),
                    instructions = arrayListOf("Primera instrucción", "Segunda instrucción", "Tercera instrucción"),
                    imageUrl = null,
                    prepTime = 210, // 3 horas y 30 minutos
                    cookTime = 30, // 30 minutos
                    servings = 3
                ),
                Recipes(
                    userId = 1,
                    categoryName = "Arroces y pastas",
                    title = "Recipe Number 4",
                    description = "Cuarta receta",
                    dificulty = "Difícil",
                    ingredients = arrayListOf("Primer ingrediente", "Segundo ingrediente", "Tercer ingrediente", "Cuarto ingrediente"),
                    instructions = arrayListOf("Primera instrucción", "Segunda instrucción", "Tercera instrucción", "Cuarta instrucción"),
                    imageUrl = null,
                    prepTime = 280, // 4 horas y 40 minutos
                    cookTime = 40, // 40 minutos
                    servings = 4
                ),
                Recipes(
                    userId = 1,
                    categoryName = "Potajes y platos de cuchara",
                    title = "Recipe Number 5",
                    description = "Quinta receta",
                    dificulty = "Muy difícil",
                    ingredients = arrayListOf("Primer ingrediente", "Segundo ingrediente", "Tercer ingrediente", "Cuarto ingrediente", "Quinto ingrediente"),
                    instructions = arrayListOf("Primera instrucción", "Segunda instrucción", "Tercera instrucción", "Cuarta instrucción", "Quinta instrucción"),
                    imageUrl = null,
                    prepTime = 350, // 5 horas y 50 minutos
                    cookTime = 50, // 50 minutos
                    servings = 5
                ),
                Recipes(
                    userId = 2,
                    categoryName = "Verduras y hortalizas",
                    title = "Receta Numero 1",
                    description = "Primera receta",
                    dificulty = "Muy fácil",
                    ingredients = arrayListOf("Primer ingrediente"),
                    instructions = arrayListOf("Primera instrucción"),
                    imageUrl = null,
                    prepTime = 70, // 1 hora y 10 minutos
                    cookTime = 10, // 10 minutos
                    servings = 1
                ),
                Recipes(
                    userId = 2,
                    categoryName = "Carnes y aves",
                    title = "Receta Numero 2",
                    description = "Segunda receta",
                    dificulty = "Fácil",
                    ingredients = arrayListOf("Primer ingrediente", "Segundo ingrediente"),
                    instructions = arrayListOf("Primera instrucción", "Segunda instrucción"),
                    imageUrl = null,
                    prepTime = 140, // 2 horas y 20 minutos
                    cookTime = 20, // 20 minutos
                    servings = 2
                ),
                Recipes(
                    userId = 2,
                    categoryName = "Pescados y mariscos",
                    title = "Receta Numero 3",
                    description = "Tercera receta",
                    dificulty = "Medio",
                    ingredients = arrayListOf("Primer ingrediente", "Segundo ingrediente", "Tercer ingrediente"),
                    instructions = arrayListOf("Primera instrucción", "Segunda instrucción", "Tercera instrucción"),
                    imageUrl = null,
                    prepTime = 210, // 3 horas y 30 minutos
                    cookTime = 30, // 30 minutos
                    servings = 3
                ),
                Recipes(
                    userId = 2,
                    categoryName = "Dulces y postres",
                    title = "Receta Numero 4",
                    description = "Cuarta receta",
                    dificulty = "Difícil",
                    ingredients = arrayListOf("Primer ingrediente", "Segundo ingrediente", "Tercer ingrediente", "Cuarto ingrediente"),
                    instructions = arrayListOf("Primera instrucción", "Segunda instrucción", "Tercera instrucción", "Cuarta instrucción"),
                    imageUrl = null,
                    prepTime = 280, // 4 horas y 40 minutos
                    cookTime = 40, // 40 minutos
                    servings = 4
                ),
                Recipes(
                    userId = 2,
                    categoryName = "Bebidas y refrescos",
                    title = "Receta Numero 5",
                    description = "Quinta receta",
                    dificulty = "Muy difícil",
                    ingredients = arrayListOf("Primer ingrediente", "Segundo ingrediente", "Tercer ingrediente", "Cuarto ingrediente", "Quinto ingrediente"),
                    instructions = arrayListOf("Primera instrucción", "Segunda instrucción", "Tercera instrucción", "Cuarta instrucción", "Quinta instrucción"),
                    imageUrl = null,
                    prepTime = 350, // 5 horas y 50 minutos
                    cookTime = 50, // 50 minutos
                    servings = 5
                )
            )

            for (recipe in recipes) {
                recipesDao.insertRecipe(recipe)
            }
        }
    }

}
