package com.example.chefhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.chefhub.navigation.AppNavigation
import com.example.chefhub.ui.theme.ChefHubTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChefHubTheme {
                AppNavigation()
            }
        }
    }
}