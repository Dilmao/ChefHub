package com.example.chefhub


class MockViewModel {
    private var userName: String = ""
    private var email: String = ""
    private var password: String = ""
    private var tries: Int = 3
    var recipe = Recipe()

    /** COMENTARIO. **/
    fun onUserChanged(value: String, field: String) {
        when (field) {
            "userName" -> userName = value
            "email" -> email = value
            "password" -> password = value
        }
    }

    fun checkLogin(callback: (Int) -> Unit) {
        when {
            email.isBlank() || password.isBlank() -> {
                callback(1)
            }
            email != "user@example.com" -> {
                callback(2)
            }
            password != "correctpassword" -> {
                tries -= 1
                callback(3)
            }
            else -> {
                callback(0)
            }
        }
    }

    fun checkRegister(confirmPassword: String, callback: (Int) -> Unit) {
        when {
            userName.isBlank() || email.isBlank() || password.isBlank() -> {
                callback(1)
            }
            "@" !in email -> {
                callback(2)
            }
            password.length !in 10..30 -> {
                callback(3)
            }
            password.none { it.isUpperCase() } || password.none { it.isLowerCase() } -> {
                callback(4)
            }
            password != confirmPassword -> {
                callback(5)
            }
            email == "existing@example.com" -> {
                callback(6)
            }
            userName == "ExistingUser" -> {
                callback(7)
            }
            else -> {
                callback(0)
            }
        }
    }

    fun recoverPassword(callback: (Int) -> Unit) {
        when {
            email.isBlank() -> {
                callback(1)
            }
            email != "registered@example.com" -> {
                callback(2)
            }
            else -> {
                callback(0)
            }
        }
    }

    /** COMENTARIO. **/
    fun resetRecipeValues() {
        recipe = Recipe()
    }

    fun onRecipeChanged(value: Any, field: String) {
        when (field) {
            "title" -> recipe = recipe.copy(title = value as String)
            "category" -> recipe = recipe.copy(category = value as String)
            "dificulty" -> recipe = recipe.copy(difficulty = value as String)
            "prepHour" -> recipe = recipe.copy(prepTime = Pair(value as Int, recipe.prepTime.second))
            "prepMin" -> recipe = recipe.copy(prepTime = Pair(recipe.prepTime.first, value as Int))
            "cookHour" -> recipe = recipe.copy(cookTime = Pair(value as Int, recipe.cookTime.first))
            "cookMin" -> recipe = recipe.copy(cookTime = Pair(recipe.cookTime.first, value as Int))
            "servings" -> recipe = recipe.copy(servings = value as Int)
        }
    }

    fun onMutableListChanged(value: String, index: Int, field: String) {
        if (field == "Ingredient") {
            recipe.ingredients[index] = value
        } else if (field == "Instruction") {
            recipe.instructions[index] = value
        }
    }

    fun onMutableListaddElement(field: String) {
        if (field == "Ingredient") {
            recipe.ingredients.add("")
        } else if (field == "Instruction") {
            recipe.instructions.add("")
        }
    }

    data class Recipe(
        val title: String = "",
        val category: String = "",
        val difficulty: String = "",
        val ingredients: MutableList<String> = mutableListOf(),
        val instructions: MutableList<String> = mutableListOf(),
        val prepTime: Pair<Int, Int> = 0 to 0,
        val cookTime: Pair<Int, Int> = 0 to 0,
        val servings: Int = 1
    )
}