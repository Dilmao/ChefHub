package com.example.chefhub.ui

import com.example.chefhub.db.SettingOption
import com.example.chefhub.db.data.Categories
import com.example.chefhub.db.data.Recipes
import com.example.chefhub.db.data.Users
import com.example.chefhub.db.settingOptions

data class AppUiState(
    /** General variables **/
    val messageText: String = "",
    val showMessage: Boolean = false,


    /** User-related variables **/
    val user: Users = Users(),
    val viewedUser: Users = Users(),
    val users: MutableList<Users> = arrayListOf(),
    val followers: MutableList<Users> = arrayListOf(),
    val following: MutableList<Users> = arrayListOf(),

    /** Recipe-related variables **/
    val recipe: Recipes = Recipes(),
    val recipes: MutableList<Recipes> = arrayListOf(),
    val favorites: MutableList<Recipes> = arrayListOf(),

    /** Category-related variables **/
    val categories: MutableList<Categories> = arrayListOf(),

    /** Search variables **/
    val search: String = "",

    /** Recipe details **/
    val prepHour: Int = 0,
    val prepMin: Int = 0,
    val cookHour: Int = 0,
    val cookMin: Int = 0,
    val servings: Int = 0,

    /** Settings screen variables **/
    val settingsOptions: List<SettingOption> = settingOptions,
)