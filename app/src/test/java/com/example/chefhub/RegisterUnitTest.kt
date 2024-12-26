package com.example.chefhub

import org.junit.Assert.assertEquals
import org.junit.Test

class RegisterUnitTest {
    private val appViewModel = MockViewModel()
    private var validationResult = -1

    // Test para campos vacíos.
    @Test
    fun testEmptyFields() {
        appViewModel.onUserChanged("", "userName")
        appViewModel.onUserChanged("", "email")
        appViewModel.onUserChanged("", "password")

        appViewModel.checkRegister("", callback = { result ->
            validationResult = result
        })

        assertEquals(1, validationResult)
    }

    // Test para validar un correo sin "@".
    @Test
    fun testInvalidEmail() {
        appViewModel.onUserChanged("User123", "userName")
        appViewModel.onUserChanged("invalidemail.com", "email")
        appViewModel.onUserChanged("Password123", "password")

        appViewModel.checkRegister("Password123", callback = { result ->
            validationResult = result
        })

        assertEquals(2, validationResult)
    }

    // Test para validar la longitud de la contraseña.
    @Test
    fun testPasswordLength() {
        appViewModel.onUserChanged("User123", "userName")
        appViewModel.onUserChanged("user@example.com", "email")
        appViewModel.onUserChanged("short", "password")

        appViewModel.checkRegister("short", callback = { result ->
            validationResult = result
        })

        assertEquals(3, validationResult)
    }

    // Test para validar mayúsculas y minúsculas en la contraseña.
    @Test
    fun testPasswordCase() {
        appViewModel.onUserChanged("User123", "userName")
        appViewModel.onUserChanged("user@example.com", "email")
        appViewModel.onUserChanged("alllowercase123", "password")

        appViewModel.checkRegister("alllowercase123", callback = { result ->
            validationResult = result
        })

        assertEquals(4, validationResult)
    }

    // Test para validar que las contraseñas coincidan.
    @Test
    fun testPasswordsMatch() {
        appViewModel.onUserChanged("User123", "userName")
        appViewModel.onUserChanged("user@example.com", "email")
        appViewModel.onUserChanged("Password123", "password")

        appViewModel.checkRegister("DiferentPassword", callback = { result ->
            validationResult = result
        })

        assertEquals(5, validationResult)
    }

    // Test para correo ya registrado.
    @Test
    fun testEmailAlredyRegistered() {
        appViewModel.onUserChanged("User123", "userName")
        appViewModel.onUserChanged("existing@example.com", "email")
        appViewModel.onUserChanged("Password123", "password")

        appViewModel.checkRegister("Password123", callback = { result ->
            validationResult = result
        })

        assertEquals(6, validationResult)
    }

    // Test para nombre ya en uso.
    @Test
    fun testUsernameAlreadyUsed() {
        appViewModel.onUserChanged("ExistingUser", "userName")
        appViewModel.onUserChanged("user@example.com", "email")
        appViewModel.onUserChanged("Password123", "password")

        appViewModel.checkRegister("Password123", callback = { result ->
            validationResult = result
        })

        assertEquals(7, validationResult)
    }

    // Test para un registro exitoso.
    @Test
    fun testSuccessfulRegistration() {
        appViewModel.onUserChanged("NewUser", "userName")
        appViewModel.onUserChanged("newuser@example.com", "email")
        appViewModel.onUserChanged("ValidPassword123", "password")

        appViewModel.checkRegister("ValidPassword123", callback = { result ->
            validationResult = result
        })

        assertEquals(0, validationResult)
    }
}