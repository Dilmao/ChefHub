package com.example.chefhub.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chefhub.db.ChefhubDB
import com.example.chefhub.db.DatabaseInitializer
import com.example.chefhub.screens.AccountScreen
import com.example.chefhub.screens.AddRecipeScreen
import com.example.chefhub.screens.InstructionsScreen
import com.example.chefhub.screens.LoginScreen
import com.example.chefhub.screens.MainScreen
import com.example.chefhub.screens.ModifyRecipeScreen
import com.example.chefhub.screens.PasswordRecoveryScreen
import com.example.chefhub.screens.RecipeScreen
import com.example.chefhub.screens.RegisterScreen
import com.example.chefhub.screens.SearchScreen
import com.example.chefhub.screens.SettingsScreen
import com.example.chefhub.ui.AppViewModel

@Composable
fun AppNavigation() {
    // COMENTARIO.
    val context = LocalContext.current
    val navController = rememberNavController()
    val database: ChefhubDB = ChefhubDB.getDatabase(context)
    val appViewModel = AppViewModel(database)

    // Inicializar la base de datos.
    LaunchedEffect(Unit) {
        DatabaseInitializer.initialize(database)
    }

    // COMENTARIO.
    NavHost(navController = navController, startDestination = AppScreens.LoginScreen.route) {
        composable(route = AppScreens.LoginScreen.route) { LoginScreen(navController, appViewModel) }
        composable(route = AppScreens.RegisterScreen.route) { RegisterScreen(navController, appViewModel) }
        composable(route = AppScreens.PasswordRecoveryScreen.route) { PasswordRecoveryScreen(navController, appViewModel) }
        composable(route = AppScreens.MainScreen.route) { MainScreen(navController, appViewModel) }
        composable(route = AppScreens.SearchScreen.route) { SearchScreen(navController, appViewModel) }
        composable(route = AppScreens.AddRecipeScreen.route) { AddRecipeScreen(navController, appViewModel) }
        composable(route = AppScreens.ModifyRecipeScreen.route) { ModifyRecipeScreen(navController, appViewModel) }
        composable(route = AppScreens.RecipeScreen.route) { RecipeScreen(navController, appViewModel) }
        composable(route = AppScreens.InstructionsScreen.route) { InstructionsScreen(navController, appViewModel) }
        composable(route = AppScreens.AccountScreen.route) { AccountScreen(navController, appViewModel) }
        composable(route = AppScreens.SettingsScreen.route) { SettingsScreen(navController, appViewModel) }
    }
}