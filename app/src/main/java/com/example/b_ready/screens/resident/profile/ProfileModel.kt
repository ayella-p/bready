package com.example.b_ready.screens.resident.profile

import com.example.b_ready.app.CustomApp
import com.example.b_ready.data.User

class ProfileModel(private val app: CustomApp) {
    fun getActiveUser(): User? = app.getCurrentUser()

    fun clearUserSession() {
        // Supposing CustomApp has a method to clear the global user object context
        // e.g., app.setCurrentUser(null)
    }
}