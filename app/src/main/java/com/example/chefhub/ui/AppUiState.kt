package com.example.chefhub.ui

import com.example.chefhub.db.SettingOption
import com.example.chefhub.db.data.Categories
import com.example.chefhub.db.data.Recipes
import com.example.chefhub.db.data.Users
import com.example.chefhub.db.settingOptions

data class AppUiState(
    /** Variables generales **/
    val messageText: String = "",
    val showMessage: Boolean = false,


    /** Variables Database **/
    val user: Users = Users(),
    val recipe: Recipes = Recipes(),
    val recipes: MutableList<Recipes> = arrayListOf(Recipes()),
    val categories: MutableList<Categories> = arrayListOf(Categories()),

    /** TODO: Nombrar **/
    val prepHour: Int = 0,
    val prepMin: Int = 0,
    val cookHour: Int = 0,
    val cookMin: Int = 0,
    val servings: Int = 0,

    /** Variables SettingsScreen **/
    val settingsOptions: List<SettingOption> = settingOptions,
)