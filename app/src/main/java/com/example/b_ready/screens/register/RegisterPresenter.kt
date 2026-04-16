package com.example.b_ready.screens.register

class RegisterPresenter(
    private val view: RegisterContract.View,
    private val model: RegisterModel
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
        model.saveNewUser(mobile, pword)
        view.showSuccessMessage()
        view.navigateToLogin()
    }
}