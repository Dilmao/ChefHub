package com.example.chefhub

import org.junit.Assert.assertEquals
import org.junit.Test

class LoginUnitTest {
    private val appViewModel = MockViewModel()
    private var validationResult = -1

    // Test para campos vacíos.
    @Test
    fun testEmptyFields() {
        appViewModel.onUserChanged("", "email")
        appViewModel.onUserChanged("", "password")

        appViewModel.checkLogin { result ->
            validationResult = result
        }

        assertEquals(1, validationResult)
    }

    // Test para validar correo existente.
    @Test
    fun testEmailNotFount() {
        appViewModel.onUserChanged("notfound@example.com", "email")
        appViewModel.onUserChanged("correctpassword", "password")

        appViewModel.checkLogin { result ->
            validationResult = result
        }

        assertEquals(2, validationResult)
    }

    // Test para verificar contraseña incorrecta.
    @Test
    fun testIncorrectPassword() {
        appViewModel.onUserChanged("user@example.com", "email")
        appViewModel.onUserChanged("wrongpassword", "password")

        appViewModel.checkLogin { result ->
            validationResult = result
        }

        assertEquals(3, validationResult)
    }

    // Test para un inicio de sesión exitoso.
    @Test
    fun testSuccessfulLogin() {
        appViewModel.onUserChanged("user@example.com", "email")
        appViewModel.onUserChanged("correctpassword", "password")

        appViewModel.checkLogin { result ->
            validationResult = result
        }

        assertEquals(0, validationResult)
    }
}