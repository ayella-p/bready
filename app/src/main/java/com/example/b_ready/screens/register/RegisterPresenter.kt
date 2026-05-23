package com.example.b_ready.screens.register

class RegisterPresenter(
    private val view: RegisterContract.View,
    private val model: RegisterModel
) : RegisterContract.Presenter {

    override fun validateRegistration(username: String, pword: String, residentId: String) {
        if (username.isEmpty() || pword.isEmpty() || residentId.isEmpty()) {
            view.showEmptyFieldsMessage()
            return
        }

        if (!model.isValidResidentId(residentId)) {
            view.showInvalidIdMessage()
            return
        }

        val isSaved = model.createResidentAccount(username, pword)

        if (isSaved) {
            view.showSuccessMessage()
            view.navigateToLogin()
        } else {
            view.showUsernameTakenMessage()
        }
    }
}