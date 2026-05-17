package com.example.b_ready.screens.admin.dashboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.b_ready.R
import com.example.b_ready.data.InventoryItem
import com.example.b_ready.data.RecentDistribution
import com.example.b_ready.db.DatabaseHelper
import com.example.b_ready.screens.resident.history.HistoryActivity
import com.example.b_ready.utils.RecentDistributionAdapter
import com.example.b_ready.utils.toast

class AdminDashboardActivity : Activity(), AdminDashboardContract.View {

    private lateinit var presenter: AdminDashboardPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        presenter = AdminDashboardPresenter(this, AdminDashboardModel(DatabaseHelper(this)))
        presenter.loadAdminData()
        findViewById<LinearLayout>(R.id.tabAdminScanner).setOnClickListener {
            presenter.onScannerTabClicked()
        }

        findViewById<LinearLayout>(R.id.tabAdminInventory).setOnClickListener {
            presenter.onInventoryTabClicked()
        }

        findViewById<LinearLayout>(R.id.tabAdminHistory).setOnClickListener {
            presenter.onHistoryTabClicked()
        }
    }


    override fun updateProgress(percent: Int, distributed: Int, remaining: Int) {
        findViewById<TextView>(R.id.tvPercent).text = "$percent%"
        findViewById<TextView>(R.id.tvDist).text = "Distributed: $distributed"
        findViewById<TextView>(R.id.tvRem).text = "Remaining: $remaining"
        findViewById<ProgressBar>(R.id.circleProgress).progress = percent
    }

    override fun displayInventory(items: List<InventoryItem>) {
        val container = findViewById<LinearLayout>(R.id.containerInventory)

        for (item in items) {
            // Create title
            val tv = TextView(this).apply {
                text = "${item.name}    ${item.currentStock}/${item.maxStock}"
                textSize = 12f
                setTextColor(android.graphics.Color.parseColor("#5C6B89"))
                setPadding(0, 16, 0, 4)
            }
            val pb = ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal).apply {
                max = 100
                progress = item.getProgressPercentage()
            }
            container.addView(tv)
            container.addView(pb)
        }
    }

    override fun displayRecentDistributions(distributions: List<RecentDistribution>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewDistributions)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecentDistributionAdapter(distributions)
    }
    override fun navigateToScanner() {
        toast("Opening QR Scanner...")
        // Uncomment when you build the Scanner screen:
        // val intent = Intent(this, ScannerActivity::class.java)
        // startActivity(intent)
        // finish() // Optional: Use finish() if you don't want them pressing "Back" to return here
    }

    override fun navigateToInventory() {
        toast("Opening Inventory Management...")
        // Uncomment when you build the Inventory screen:
        // val intent = Intent(this, AdminInventoryActivity::class.java)
        // startActivity(intent)
        // finish()
    }

    override fun navigateToHistory() {
        toast("Opening Admin History...")
        // Uncomment when you build the History screen:
        // val intent = Intent(this, AdminHistoryActivity::class.java)
        // startActivity(intent)
        // finish()
    }
}