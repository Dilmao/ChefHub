package com.example.chefhub.ui

data class AppUiState(
    /** Variables LoginScreen **/
    val email: String = "",
    val password: String = "",

    /** Variables RegisterScreen **/
    val newUsuario: String = "",
    val newEmail: String = "",
    val newPassword: String = "",
    val confirmNewPassword: String = "",

    /** Variables PasswordRecoveryScreen **/
    val recoveryEmail: String = "",

    /** -variables AddRecipe **/
    val recipeTitle: String = "",
    val ingredientList: MutableList<String> = arrayListOf(""),
    val instructionsList: MutableList<String> = arrayListOf(""),


    /** Variables generales **/
    val messageText: String = "",
    val showMessage: Boolean = false,
)