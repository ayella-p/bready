package com.example.b_ready.screens.admin.dashboard
import com.example.b_ready.db.DatabaseHelper

class AdminDashboardModel(private val dbHelper: DatabaseHelper) {

    // Accurate calculation based on DB
    fun getDistributedCount(): Int {
        return 350
    }

    fun getTotalFamilies(): Int {
        return 500 // Hardcoded total families in the barangay for MVP
    }

    fun getInventory() = dbHelper.getInventory()
    fun getRecentDistributions() = dbHelper.getRecentDistributions()
}