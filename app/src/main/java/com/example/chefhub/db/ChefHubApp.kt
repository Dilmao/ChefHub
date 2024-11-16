package com.example.chefhub.db

import android.app.Application

class ChefHubApp : Application() {
    val database: ChefhubDB by lazy { ChefhubDB.getDatabase(this) }
}