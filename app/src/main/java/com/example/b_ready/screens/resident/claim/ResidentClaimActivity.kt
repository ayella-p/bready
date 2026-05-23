package com.example.b_ready.screens.resident.claim

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.b_ready.R
import com.example.b_ready.app.CustomApp
import com.example.b_ready.db.DatabaseHelper
import com.example.b_ready.utils.toast

    class ResidentClaimActivity : Activity(), ResidentClaimContract.View {

    private lateinit var presenter: ResidentClaimPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resident_claim)

        val dbHelper = DatabaseHelper(this)
        presenter = ResidentClaimPresenter(this, ResidentClaimModel(application as CustomApp), dbHelper)
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        // Generate data updates
        presenter.generateResidentClaimDetails()
    }

    override fun displayClaimId(formattedId: String) {
        findViewById<TextView>(R.id.tvClaimIdDisplay).text = formattedId
    }

    override fun showErrorState() {
        toast("Session Error: Please restart or re-login.")
        finish()
    }
    override fun showAsClaimed() {
            val idDisplay = findViewById<TextView>(R.id.tvClaimIdDisplay)

            // Convert text card elements to green success styling matching design logic
            idDisplay.text = "CLAIMED SUCCESSFULLY"
            idDisplay.setTextColor(android.graphics.Color.parseColor("#4CAF50"))
            idDisplay.backgroundTintList = android.content.res.ColorStateList.valueOf(
                android.graphics.Color.parseColor("#E8F5E9")
            )
        }
}