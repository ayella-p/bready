package com.example.b_ready.screens.admin.history

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import com.example.b_ready.R
import com.example.b_ready.data.Transaction
import com.example.b_ready.db.DatabaseHelper
import com.example.b_ready.screens.admin.dashboard.AdminDashboardActivity
import com.example.b_ready.utils.TransactionAdapter
import com.example.b_ready.utils.toast
import com.google.android.material.button.MaterialButton

class AdminHistoryActivity : Activity(), AdminHistoryContract.View {

    private lateinit var presenter: AdminHistoryPresenter

    // UI Variables
    private lateinit var btnFilterAll: MaterialButton
    private lateinit var btnFilterRelief: MaterialButton
    private lateinit var btnFilterBookings: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_history)

        presenter = AdminHistoryPresenter(this, AdminHistoryModel(DatabaseHelper(this)))

        // Initialize UI Elements
        btnFilterAll = findViewById(R.id.btnFilterAll)
        btnFilterRelief = findViewById(R.id.btnFilterRelief)
        btnFilterBookings = findViewById(R.id.btnFilterBookings)

        // Back Button
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            presenter.onBackButtonClicked()
        }

        // Filter Clicks
        btnFilterAll.setOnClickListener { presenter.onFilterClicked("All") }
        btnFilterRelief.setOnClickListener { presenter.onFilterClicked("Relief") }
        btnFilterBookings.setOnClickListener { presenter.onFilterClicked("Bookings") }

        // Load Data (Defaults to "All")
        presenter.loadHistory()
    }

    override fun displayTransactions(transactions: List<Transaction>) {
        val listView = findViewById<ListView>(R.id.listViewAdminHistory)
        val adapter = TransactionAdapter(this, transactions)
        listView.adapter = adapter
    }

    override fun showToast(message: String) {
        toast(message)
    }

    override fun updateFilterUI(activeFilter: String) {
        // Define our colors
        val activeBg = ColorStateList.valueOf(Color.parseColor("#1B3A6D"))
        val inactiveBg = ColorStateList.valueOf(Color.WHITE)
        val activeText = Color.WHITE
        val inactiveText = Color.parseColor("#1B3A6D")

        // 1. Reset ALL buttons to inactive (White background, Blue text)
        btnFilterAll.backgroundTintList = inactiveBg
        btnFilterAll.setTextColor(inactiveText)

        btnFilterRelief.backgroundTintList = inactiveBg
        btnFilterRelief.setTextColor(inactiveText)

        btnFilterBookings.backgroundTintList = inactiveBg
        btnFilterBookings.setTextColor(inactiveText)

        // 2. Highlight ONLY the active one (Blue background, White text)
        when (activeFilter) {
            "All" -> {
                btnFilterAll.backgroundTintList = activeBg
                btnFilterAll.setTextColor(activeText)
            }
            "Relief" -> {
                btnFilterRelief.backgroundTintList = activeBg
                btnFilterRelief.setTextColor(activeText)
            }
            "Bookings" -> {
                btnFilterBookings.backgroundTintList = activeBg
                btnFilterBookings.setTextColor(activeText)
            }
        }
    }


    override fun navigateToDashboard() {
        val intent = Intent(this, AdminDashboardActivity::class.java)
        startActivity(intent)
        finish()

    }
}