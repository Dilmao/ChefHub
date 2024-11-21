package com.example.chefhub.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    // Convierte el ArrayList<String> en un String (JSON)
    @TypeConverter
    fun fromIngredients(ingredients: ArrayList<String>?): String? {
        return Gson().toJson(ingredients)
    }

    // Convierte el String (JSON) de vuelta a un ArrayList<String>
    @TypeConverter
    fun toIngredients(ingredientsString: String?): ArrayList<String>? {
        val type = object : TypeToken<ArrayList<String>>() {}.type
        return Gson().fromJson(ingredientsString, type)
    }
}