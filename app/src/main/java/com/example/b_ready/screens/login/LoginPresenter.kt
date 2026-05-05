package com.example.b_ready.screens.login

import com.example.b_ready.app.CustomApp

class LoginPresenter(private val view: LoginContract.View,
                     private val model: LoginModel) : LoginContract.Presenter {

    override fun validateCredentials(mobile: String, pword: String) {
        if (mobile.isEmpty() || pword.isEmpty()) {
            view.showEmptyMessage()
            return
        }

        val savedUser = model.getSavedUser()
        if (mobile == savedUser.mobileNumber && pword == savedUser.password) {
            view.showSuccessMessage()
            view.showDashboardScreen()
        } else {
            view.showInvalidCredential()
        }
    }
}
