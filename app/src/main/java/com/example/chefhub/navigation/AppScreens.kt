package com.example.chefhub.navigation

sealed class AppScreens (val route: String) {
    data object LoginScreen: AppScreens(route = "login_screen")
    data object RegisterScreen: AppScreens(route = "register_screen")
    data object PasswordRecoveryScreen: AppScreens(route = "password_recovery_screen")
    data object MainScreen: AppScreens(route = "main_screen")
    data object SearchScreen: AppScreens(route = "search_screen")
    data object AddRecipeScreen: AppScreens(route = "add_recipe_screen")
    data object ModifyRecipeScreen: AppScreens(route = "modify_recipe_screen")
    data object RecipeScreen: AppScreens(route = "recipe_screen")
    data object InstructionsScreen: AppScreens(route = "instructions_screen")
    data object AccountScreen: AppScreens(route = "account_screen")
    data object SettingsScreen: AppScreens(route = "settings_screen")
}