package com.example.b_ready.screens.register

class RegisterPresenter(
    private val view: RegisterContract.View
) : RegisterContract.Presenter {

    override fun validateRegistration(mobile: String, pword: String, isIdUploaded: Boolean) {
        if (mobile.isEmpty() || pword.isEmpty()) {
            view.showEmptyMessage()
            return
        }

        if (!isIdUploaded) {
            view.showMissingIdMessage()
            return
        }
        view.showSuccessMessage()
        view.navigateToLogin()
    }
}