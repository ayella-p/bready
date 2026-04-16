package com.example.b_ready.screens.register

class RegisterContract {
    interface View {
        fun showEmptyMessage()
        fun showMissingIdMessage()
        fun showSuccessMessage()
        fun navigateToLogin()
    }

    interface Presenter {
        fun validateRegistration(mobile: String, pword: String, isIdUploaded: Boolean)
    }
}