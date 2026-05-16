package com.example.b_ready.screens.admin.dashboard
import com.example.b_ready.data.InventoryItem
import com.example.b_ready.data.RecentDistribution

class AdminDashboardContract {
    interface View {
        fun updateProgress(percent: Int, distributed: Int, remaining: Int)
        fun displayInventory(items: List<InventoryItem>)
        fun displayRecentDistributions(distributions: List<RecentDistribution>)
    }
    interface Presenter {
        fun loadAdminData()
    }
}