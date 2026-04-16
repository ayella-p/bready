package com.example.b_ready.screens.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.b_ready.R
import com.example.b_ready.app.CustomApp
import com.example.b_ready.screens.register.RegisterActivity
import com.example.b_ready.utils.getEditTextValue
import com.example.b_ready.utils.toast

class LoginActivity : Activity(), LoginContract.View {

    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = LoginPresenter(this, application as CustomApp)


        findViewById<Button>(R.id.btnSignIn).setOnClickListener {
            val mobileInput = getEditTextValue(R.id.etMobileNumber)
            val passwordInput = getEditTextValue(R.id.etPassword)
            presenter.validateCredentials(mobileInput, passwordInput)
        }


        findViewById<TextView>(R.id.tvRegister).setOnClickListener {
            // Create the intent
            val intent = Intent(this, RegisterActivity::class.java)
            // Start the new screen
            startActivity(intent)

            // Note: We DO NOT put finish() here, so the user can still press
            // the 'Back' button on their phone to return to this Login screen!
        }
    }

    override fun showEmptyMessage() {
        toast("Please enter your mobile number and password")
    }

    override fun showSuccessMessage() {
        toast("Welcome back to B-Ready!")
    }

    override fun showInvalidCredential() {
        toast("Invalid Mobile Number or Password")
    }

    override fun showDashboardScreen() {

    }
}