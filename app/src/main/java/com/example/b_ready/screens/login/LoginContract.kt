package com.example.b_ready.screens.login

class LoginContract {
    interface View {
        fun showEmptyMessage()
        fun showSuccessMessage()
        fun showDashboardScreen()
        fun showInvalidCredential()
    }

    interface Presenter {
        fun validateCredentials(username: String, pword: String)
    }
}