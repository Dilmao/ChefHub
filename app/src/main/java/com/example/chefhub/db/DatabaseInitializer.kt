package com.example.chefhub.db

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.firstOrNull
import com.example.chefhub.db.data.Categories

object DatabaseInitializer {

    // Inicializa los datos de la base de datos, como las categorías predefinidas.
    fun initialize(database: ChefhubDB) {
        // Usamos un CoroutineScope para realizar la operación de forma asíncrona.
        CoroutineScope(Dispatchers.IO).launch {
            addDefaultCategories(database)
        }
    }

    //Agrega las categorías por defecto si no existen en la base de datos.
    private suspend fun addDefaultCategories(database: ChefhubDB) {
        val categoriesDao = database.categoriesDao
        val existingCategories = categoriesDao.getCategories().firstOrNull()

        // Solo agregar si no existen categorías.
        if (existingCategories.isNullOrEmpty()) {
            val categories = listOf(
                "Alimentación infantil",
                "Aperitivos y tapas",
                "Sopas y cremas",
                "Arroces y pastas",
                "Potajes y platos de cuchara",
                "Verduras y hortalizas",
                "Carnes y aves",
                "Pescados y mariscos",
                "Dulces y postres",
                "Bebidas y refrescos",
                "Masas y repostería",
                "Salsas",
                "Guarniciones y acompañamientos",
                "Básicas",
                "Dietas trituradas",
                "Navidad"
            )

            for (categoryName in categories) {
                val category = Categories(categoryName = categoryName)
                categoriesDao.insertCategory(category)
            }
        }
    }
}
