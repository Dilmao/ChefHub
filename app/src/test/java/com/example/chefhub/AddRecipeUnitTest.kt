package com.example.chefhub

import org.junit.Assert.assertEquals
import org.junit.Test

class AddRecipeUnitTest {
    private val appViewModel = MockViewModel()

    // Test para la inicialización de las valores de la receta.
    @Test
    fun testResetRecipeValues() {
        appViewModel.resetRecipeValues()

        assertEquals("", appViewModel.recipe.title)
        assertEquals("", appViewModel.recipe.category)
        assertEquals("", appViewModel.recipe.difficulty)
        assertEquals(0 to 0, appViewModel.recipe.prepTime)
        assertEquals(0 to 0, appViewModel.recipe.cookTime)
        assertEquals(1, appViewModel.recipe.servings)
        assertEquals(0, appViewModel.recipe.ingredients.size)
        assertEquals(0, appViewModel.recipe.instructions.size)
    }

    // Test para actualizar el título de la receta.
    @Test
    fun testUpdatedRecipeTitle() {
        appViewModel.onRecipeChanged("Tarta muchismo muy buena", "title")

        assertEquals("Tarta muchismo muy buena", appViewModel.recipe.title)
    }

    // Test para agregar una categoría.
    @Test
    fun testUpdateRecipeCategory() {
        appViewModel.onRecipeChanged("Postres", "category")

        assertEquals("Postres", appViewModel.recipe.category)
    }

    // Test para añadir un ingrediente.
    @Test
    fun testAddIngredient() {
        appViewModel.onMutableListaddElement("Ingredient")
        appViewModel.onMutableListChanged("Sugar", 0, "Ingredient")

        assertEquals(1, appViewModel.recipe.ingredients.size)
        assertEquals("Sugar", appViewModel.recipe.ingredients[0])
    }

    // Test para añadir una instrucción.
    @Test
    fun testAddInstruction() {
        appViewModel.onMutableListaddElement("Instruction")
        appViewModel.onMutableListChanged("Snif the sugar", 0, "Instruction")

        assertEquals(1, appViewModel.recipe.instructions.size)
        assertEquals("Snif the sugar", appViewModel.recipe.instructions[0])
    }

    // Test para actualizar el tiempo de preparación.
    @Test
    fun testUpdatePrepTime() {
        appViewModel.onRecipeChanged(2, "prepHour")
        appViewModel.onRecipeChanged(30, "prepMin")

        assertEquals(2 to 30, appViewModel.recipe.prepTime)
    }

    // Test para actualizar el tiempo de cocción.
    @Test
    fun testUpdateCookTime() {
        appViewModel.onRecipeChanged(2, "cookHour")
        appViewModel.onRecipeChanged(30, "cookMin")

        assertEquals(2 to 30, appViewModel.recipe.cookTime)
    }

    // Test para actualizar las raciones.
    @Test
    fun testUpdateServings() {
        appViewModel.onRecipeChanged(4, "servings")

        assertEquals(4, appViewModel.recipe.servings)
    }
}