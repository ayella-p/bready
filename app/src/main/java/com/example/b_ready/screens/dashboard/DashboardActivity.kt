package com.example.b_ready.screens.dashboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.b_ready.R
import com.example.b_ready.app.CustomApp
import com.example.b_ready.utils.toast

class DashboardActivity : Activity(), DashboardContract.View {

    private lateinit var presenter: DashboardPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        presenter = DashboardPresenter(this, DashboardModel(application as CustomApp))

        presenter.loadDashboardData()

        findViewById<Button>(R.id.btnClaim).setOnClickListener {
            presenter.onClaimButtonClicked()
        }

        findViewById<CardView>(R.id.cardRequestAid).setOnClickListener {
            presenter.onRequestAidClicked()
        }

        findViewById<CardView>(R.id.cardBorrowEquipment).setOnClickListener {
            presenter.onBorrowEquipmentClicked()
        }


        findViewById<LinearLayout>(R.id.tabHistory).setOnClickListener {
            presenter.onHistoryTabClicked()
        }

        findViewById<LinearLayout>(R.id.tabProfile).setOnClickListener {
            presenter.onProfileTabClicked()
        }
    }


    override fun displayUserName(name: String) {
       // findViewById<TextView>(R.id.tvUserName).text = name
    }
    override fun displayWalletBalance(balance: String) {
        // findViewById<TextView>(R.id.tvBalance).text = balance
    }
    override fun showToastMessage(message: String) {
        toast(message)
    }
    override fun navigateToHistory() {
        toast("Navigating to History Screen")
        // val intent = Intent(this, HistoryActivity::class.java)
        // startActivity(intent)
    }
    override fun navigateToProfile() {
        toast("Navigating to Profile Screen")
    }

    override fun showClaimScreen() {
        toast("Opening QR Code Scanner...")
    }

    override fun showRequestAidScreen() {
        toast("Opening Aid Request Form...")
    }

    override fun showBorrowEquipmentScreen() {
        toast("Opening Equipment Inventory...")
    }
}
