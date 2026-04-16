package com.example.b_ready.screens.login

import com.example.b_ready.app.CustomApp

class LoginPresenter(private val view: LoginContract.View,private val app: CustomApp) : LoginContract.Presenter {

    override fun validateCredentials(mobile: String, pword: String) {
        if (mobile.isEmpty() || pword.isEmpty()) {
            view.showEmptyMessage()
            return
        }

        val savedUser = app.getUser()
        if (mobile == savedUser.mobileNumber && pword == savedUser.password) {
            view.showSuccessMessage()
            view.showDashboardScreen()
        } else {
            view.showInvalidCredential()
        }
    }
}