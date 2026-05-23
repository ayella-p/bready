package com.example.b_ready.screens.resident.dashboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.b_ready.R
import com.example.b_ready.app.CustomApp
import com.example.b_ready.db.DatabaseHelper
import com.example.b_ready.screens.resident.borrow.BorrowActivity
import com.example.b_ready.screens.resident.claim.ResidentClaimActivity
import com.example.b_ready.screens.resident.history.HistoryActivity
import com.example.b_ready.screens.resident.profile.ProfileActivity
import com.example.b_ready.utils.toast

class DashboardActivity : Activity(), DashboardContract.View {

    private lateinit var presenter: DashboardPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resident_dashboard)

        val dbHelper = DatabaseHelper(this)
        val model = DashboardModel(application as CustomApp, dbHelper)
        presenter = DashboardPresenter(this, model)

        findViewById<Button>(R.id.btnClaim).setOnClickListener {
            presenter.onClaimButtonClicked()
        }

        findViewById<LinearLayout>(R.id.cardRequestAid).setOnClickListener {
            presenter.onClaimButtonClicked()
        }

        findViewById<LinearLayout>(R.id.cardBorrowEquipment).setOnClickListener {
            presenter.onBorrowEquipmentClicked()
        }

        findViewById<LinearLayout>(R.id.tabHistory).setOnClickListener {
            presenter.onHistoryTabClicked()
        }
        findViewById<CardView>(R.id.cardRecentActivity).setOnClickListener {
            presenter.onHistoryTabClicked()
        }
        findViewById<TextView>(R.id.txtViewALl).setOnClickListener {
            presenter.onHistoryTabClicked()
        }
        findViewById<LinearLayout>(R.id.tabProfile).setOnClickListener {
            presenter.onProfileTabClicked()
        }
    }

    override fun setReliefCardToClaimedState() {
        val container = findViewById<LinearLayout>(R.id.layoutReliefContainer)
        val statusLabel = findViewById<TextView>(R.id.tvReliefStatusLabel)
        val claimBtn = findViewById<Button>(R.id.btnClaim)

        // Hide action trigger button completely
        claimBtn.visibility = android.view.View.GONE

        // Switch descriptive text and text hex colors
        statusLabel.text = "Claimed Successfully"
        statusLabel.setTextColor(android.graphics.Color.parseColor("#4CAF50"))

        // Re-tint container border from warning orange to soft forest green accent
        container.backgroundTintList = android.content.res.ColorStateList.valueOf(
            android.graphics.Color.parseColor("#E8F5E9")
        )
    }
    override fun onResume() {
        super.onResume()
        presenter.loadDashboardData()
    }

    override fun displayUserName(name: String) {
        findViewById<TextView>(R.id.tvUserName).text = name
    }

    override fun displayWalletBalance(balance: String) {
        findViewById<TextView>(R.id.tvBalance).text = balance
    }

    override fun showToastMessage(message: String) {
        toast(message)
    }

    override fun navigateToHistory() {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }

    override fun navigateToProfile() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    override fun showClaimScreen() {
        val intent = Intent(this, ResidentClaimActivity::class.java)
        startActivity(intent)
    }

    override fun showRequestAidScreen() {
        toast("Opening Aid Request Form...")
    }

    override fun showBorrowEquipmentScreen() {
        val intent = Intent(this, BorrowActivity::class.java)
        startActivity(intent)
    }
    override fun setReliefCardToDefaultState() {
        val container = findViewById<LinearLayout>(R.id.layoutReliefContainer)
        val statusLabel = findViewById<TextView>(R.id.tvReliefStatusLabel)
        val claimBtn = findViewById<Button>(R.id.btnClaim)

        // Make the claim button visible again
        claimBtn.visibility = android.view.View.VISIBLE

        // Reset text messages and colors back to original theme styling
        statusLabel.text = "Ready for claiming"
        statusLabel.setTextColor(android.graphics.Color.parseColor("#FF7043"))

        // Re-tint container border back to warning orange layout scheme
        container.backgroundTintList = android.content.res.ColorStateList.valueOf(
            android.graphics.Color.parseColor("#FFFFFF") // Or transparent/original color
        )
    }
}