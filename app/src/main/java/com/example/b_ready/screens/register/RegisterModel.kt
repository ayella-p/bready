package com.example.b_ready.screens.register

import com.example.b_ready.data.User
import com.example.b_ready.db.DatabaseHelper

class RegisterModel(private val dbHelper: DatabaseHelper) {

    // Saves the user profile into the real SQLite database
    fun createResidentAccount(username: String, pword: String): Boolean {
        val newUser = User(username, pword, "Resident")
        return dbHelper.registerUser(newUser)
    }

    // A simple business logic rule to verify if the ID input looks realistic for our MVP
    fun isValidResidentId(residentId: String): Boolean {
        // Checks that the ID isn't empty and has a prefix format like "BR-"
        return residentId.isNotEmpty() && residentId.uppercase().startsWith("BR-")
    }
}