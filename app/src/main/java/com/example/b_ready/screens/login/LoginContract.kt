package com.example.b_ready.screens.login

class LoginContract {
    interface View {
        fun showEmptyMessage()
        fun showSuccessMessage(role: String) // Modified to show role
        fun showInvalidCredential()
        fun showResidentDashboard() // New
        fun showAdminDashboard()    // New
    }

    interface Presenter {
        fun validateCredentials(username: String, pword: String)
    }
}