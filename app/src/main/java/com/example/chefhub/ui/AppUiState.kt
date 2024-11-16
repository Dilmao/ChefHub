package com.example.chefhub.ui

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import com.example.chefhub.db.SettingOption
import com.example.chefhub.db.settingOptions

data class AppUiState(
    /** Variables generales **/
    val messageText: String = "",
    val showMessage: Boolean = false,


    /** Variables Usuario **/
    val userId: Long = -1,
    val user: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",

    /** Variables AddRecipeScreen **/
    val recipeTitle: String = "",
    val ingredientList: MutableList<String> = arrayListOf(""),
    val instructionsList: MutableList<String> = arrayListOf(""),

    /** Variables AccountScreen **/
    val drawerState: DrawerState = DrawerState(initialValue = DrawerValue.Closed),

    /** Variables SettingsScreen **/
    val settingsOptions: List<SettingOption> = settingOptions,
)