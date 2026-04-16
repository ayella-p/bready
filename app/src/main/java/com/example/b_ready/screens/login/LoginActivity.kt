package com.example.b_ready.screens.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.b_ready.R
import com.example.b_ready.app.CustomApp
import com.example.b_ready.screens.dashboard.DashboardActivity
import com.example.b_ready.screens.register.RegisterActivity
import com.example.b_ready.utils.getEditTextValue
import com.example.b_ready.utils.toast
class LoginActivity : Activity(), LoginContract.View {

    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter = LoginPresenter(this, LoginModel(application as CustomApp))
        findViewById<Button>(R.id.btnSignIn).setOnClickListener {
            val mobileInput = getEditTextValue(R.id.etMobileNumber)
            val passwordInput = getEditTextValue(R.id.etPassword)
            presenter.validateCredentials(mobileInput, passwordInput)
        }
        findViewById<TextView>(R.id.tvRegister).setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

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
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }
}