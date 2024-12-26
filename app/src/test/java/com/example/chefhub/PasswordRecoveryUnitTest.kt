package com.example.chefhub

import org.junit.Assert.assertEquals
import org.junit.Test

class PasswordRecoveryUnitTest {
    private val appViewModel = MockViewModel()
    private var validationResult = -1

    // Test para campo vacio.
    @Test
    fun testEmptyEmailField() {
        appViewModel.onUserChanged("", "email")

        appViewModel.recoverPassword { result ->
            validationResult = result
        }

        assertEquals(1, validationResult)
    }

    // Test para correo no registrado.
    @Test
    fun testUnregisteredEmail() {
        appViewModel.onUserChanged("unregistered@example.com", "email")

        appViewModel.recoverPassword { result ->
            validationResult = result
        }

        assertEquals(2, validationResult)
    }

    // Test oara recuperación exitosa.
    @Test
    fun testSuccesfulRecovery() {
        appViewModel.onUserChanged("registered@example.com", "email")

        appViewModel.recoverPassword { result ->
            validationResult = result
        }

        assertEquals(0, validationResult)
    }
}