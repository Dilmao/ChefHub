package com.example.chefhub.ui

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import com.example.chefhub.data.SettingOption
import com.example.chefhub.data.settingOptions

data class AppUiState(
    /** Variables generales **/
    val messageText: String = "",
    val showMessage: Boolean = false,
    val userId: Long = 0,


    /** Variables LoginScreen **/
    val email: String = "",
    val password: String = "",

    /** Variables RegisterScreen **/
    val newUser: String = "",
    val newEmail: String = "",
    val newPassword: String = "",
    val confirmNewPassword: String = "",

    /** Variables PasswordRecoveryScreen **/
    val recoveryEmail: String = "",

    /** Variables AddRecipeScreen **/
    val recipeTitle: String = "",
    val ingredientList: MutableList<String> = arrayListOf(""),
    val instructionsList: MutableList<String> = arrayListOf(""),

    /** Variables AccountScreen **/
    val drawerState: DrawerState = DrawerState(initialValue = DrawerValue.Closed),

    /** Variables SettingsScreen **/
    val settingsOptions: List<SettingOption> = settingOptions,
)