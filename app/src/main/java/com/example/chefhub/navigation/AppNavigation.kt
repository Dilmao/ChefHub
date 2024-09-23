package com.example.chefhub.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chefhub.screens.AccountScreen
import com.example.chefhub.screens.AddRecipeScreen
import com.example.chefhub.screens.LoginScreen
import com.example.chefhub.screens.MainScreen
import com.example.chefhub.screens.PasswordRecoveryScreen
import com.example.chefhub.screens.RegisterScreen
import com.example.chefhub.ui.AppViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val appViewModel = AppViewModel()

    NavHost(navController = navController, startDestination = AppScreens.LoginScreen.route) {
        composable(route = AppScreens.LoginScreen.route) { LoginScreen(navController, appViewModel) }
        composable(route = AppScreens.RegisterScreen.route) { RegisterScreen(navController, appViewModel) }
        composable(route = AppScreens.PasswordRecoveryScreen.route) { PasswordRecoveryScreen(navController, appViewModel) }
        composable(route = AppScreens.MainScreen.route) { MainScreen(navController, appViewModel) }
        composable(route = AppScreens.AddRecipeScreen.route) { AddRecipeScreen(navController, appViewModel) }
        composable(route = AppScreens.AccountScreen.route) { AccountScreen(navController, appViewModel) }
    }
}