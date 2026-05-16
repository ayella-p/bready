package com.example.b_ready.screens.login

import com.example.b_ready.app.CustomApp
import com.example.b_ready.data.User
import com.example.b_ready.db.DatabaseHelper

class LoginModel(private val app: CustomApp, private val dbHelper: DatabaseHelper) {

    // Ask the DB if this user exists
    fun attemptLogin(username: String, pword: String): User? {
        return dbHelper.authenticateUser(username, pword)
    }

    // Save them to the global session so the Dashboard knows who they are
    fun saveSession(user: User) {
        app.setCurrentUser(user)
    }
}