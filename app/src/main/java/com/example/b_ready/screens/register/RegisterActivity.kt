package com.example.b_ready.screens.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.b_ready.R
import com.example.b_ready.db.DatabaseHelper
import com.example.b_ready.screens.login.LoginActivity
import com.example.b_ready.utils.getEditTextValue
import com.example.b_ready.utils.toast

class RegisterActivity : Activity(), RegisterContract.View {

    private lateinit var presenter: RegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Instantiate the model with your real DatabaseHelper context
        val dbHelper = DatabaseHelper(this)
        presenter = RegisterPresenter(this, RegisterModel(dbHelper))

        findViewById<Button>(R.id.btnCreateAccount).setOnClickListener {
            val usernameInput = getEditTextValue(R.id.etRegUsername)
            val passwordInput = getEditTextValue(R.id.etRegPassword)
            val residentIdInput = getEditTextValue(R.id.etRegResidentId)

            presenter.validateRegistration(usernameInput, passwordInput, residentIdInput)
        }

        findViewById<TextView>(R.id.tvSignIn).setOnClickListener {
            navigateToLogin()
        }
    }

    override fun showEmptyFieldsMessage() {
        toast("Please fill in all layout fields.")
    }

    override fun showInvalidIdMessage() {
        toast("Invalid ID format! Must start with 'BR-' (e.g. BR-1234)")
    }

    override fun showUsernameTakenMessage() {
        toast("Username is already taken! Please pick a different one.")
    }

    override fun showSuccessMessage() {
        toast("Account created successfully!")
    }

    override fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}