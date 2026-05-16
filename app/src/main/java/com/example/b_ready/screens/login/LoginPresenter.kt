package com.example.b_ready.screens.login

class LoginPresenter(
    private val view: LoginContract.View,
    private val model: LoginModel
) : LoginContract.Presenter {

    override fun validateCredentials(username: String, pword: String) {
        if (username.isEmpty() || pword.isEmpty()) {
            view.showEmptyMessage()
            return
        }

        // 1. Ask the Model to check the Database
        val user = model.attemptLogin(username, pword)

        // 2. Decide what to do
        if (user != null) {
            // Login Success!
            model.saveSession(user) // Save them to CustomApp
            view.showSuccessMessage(user.role)

            // Route based on role
            if (user.role == "Admin") {
                view.showAdminDashboard()
            } else {
                view.showResidentDashboard()
            }
        } else {
            // Login Failed
            view.showInvalidCredential()
        }
    }
}