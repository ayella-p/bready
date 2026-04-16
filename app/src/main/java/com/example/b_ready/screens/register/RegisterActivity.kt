package com.example.b_ready.screens.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.b_ready.screens.login.LoginActivity
import com.example.b_ready.utils.getEditTextValue
import com.example.b_ready.utils.toast
import com.example.b_ready.R

class RegisterActivity : Activity(), RegisterContract.View {

    private lateinit var presenter: RegisterPresenter

    private var isIdUploaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        presenter = RegisterPresenter(this)

        findViewById<Button>(R.id.btnUploadId).setOnClickListener {
            //successful for now
            isIdUploaded = true
            (it as Button).text = "ID Uploaded Successfully"
            it.setTextColor(android.graphics.Color.parseColor("#4CAF50")) // Turn text green
            toast("ID Attached")
        }

        findViewById<Button>(R.id.btnCreateAccount).setOnClickListener {
            val mobileInput = getEditTextValue(R.id.etRegMobileNumber)
            val passwordInput = getEditTextValue(R.id.etRegPassword)

            presenter.validateRegistration(mobileInput, passwordInput, isIdUploaded)
        }

        findViewById<TextView>(R.id.tvSignIn).setOnClickListener {
            navigateToLogin()
        }
    }

    override fun showEmptyMessage() {
        toast("Please fill in your mobile number and password.")
    }

    override fun showMissingIdMessage() {
        toast("Please upload a valid Resident ID.")
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