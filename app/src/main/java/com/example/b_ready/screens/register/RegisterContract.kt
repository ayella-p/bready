package com.example.b_ready.screens.register

class RegisterContract {
    interface View {
        fun showEmptyFieldsMessage()
        fun showInvalidIdMessage()
        fun showUsernameTakenMessage()
        fun showSuccessMessage()
        fun navigateToLogin()
    }

    interface Presenter {
        fun validateRegistration(username: String, pword: String, residentId: String)
    }
}