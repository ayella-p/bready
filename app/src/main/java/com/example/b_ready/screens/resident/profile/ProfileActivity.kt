package com.example.b_ready.screens.resident.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.b_ready.R
import com.example.b_ready.app.CustomApp
import com.example.b_ready.screens.login.LoginActivity
import com.google.android.material.button.MaterialButton

class ProfileActivity : Activity(), ProfileContract.View {

    private lateinit var presenter: ProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        presenter = ProfilePresenter(this, ProfileModel(application as CustomApp))

        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            val currentUser = (application as CustomApp).getCurrentUser()

            if (currentUser?.role == "Admin") {
                // If Admin, go back to Admin Dashboard
                val intent = Intent(this, com.example.b_ready.screens.admin.dashboard.AdminDashboardActivity::class.java)
                startActivity(intent)
            } else {
                // If Resident, just close and go back to Resident Dashboard
                finish()
            }
            finish() }

        findViewById<MaterialButton>(R.id.btnLogout).setOnClickListener {
            presenter.handleLogout()
        }

        presenter.loadUserProfile()
    }

    override fun displayUserInfo(username: String, role: String) {
        findViewById<TextView>(R.id.tvProfileUsername).text = username
        findViewById<TextView>(R.id.tvProfileRole).text = role
    }

    override fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        // Clear all backstack layers so the user can't hit back to go back into the dashboard
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}