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

class AdminDashboardActivity : Activity(), AdminDashboardContract.View {

    private lateinit var presenter: AdminDashboardPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        presenter = AdminDashboardPresenter(this, AdminDashboardModel(DatabaseHelper(this)))
        presenter.loadAdminData()
    }


    override fun updateProgress(percent: Int, distributed: Int, remaining: Int) {
        findViewById<TextView>(R.id.tvPercent).text = "$percent%"
        findViewById<TextView>(R.id.tvDist).text = "Distributed: $distributed"
        findViewById<TextView>(R.id.tvRem).text = "Remaining: $remaining"
        findViewById<ProgressBar>(R.id.circleProgress).progress = percent
    }

    // Dynamically adds progress bars based on Database!
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
            // Create standard horizontal progress bar
            val pb = ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal).apply {
                max = 100
                progress = item.getProgressPercentage() // Accurate math call!
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
    
}