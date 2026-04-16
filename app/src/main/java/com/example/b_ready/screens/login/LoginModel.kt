package com.example.b_ready.screens.login

import com.example.b_ready.app.CustomApp
import com.example.b_ready.data.User

class LoginModel(private val app: CustomApp) {
    fun getSavedUser(): User {
        return app.getUser()
    }
}