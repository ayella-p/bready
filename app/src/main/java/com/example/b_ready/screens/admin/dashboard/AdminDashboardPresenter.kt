package com.example.b_ready.screens.admin.dashboard

import com.example.b_ready.screens.admin.dashboard.AdminDashboardContract
import com.example.b_ready.screens.admin.dashboard.AdminDashboardModel

class AdminDashboardPresenter(
    private val view: AdminDashboardContract.View,
    private val model: AdminDashboardModel
) : AdminDashboardContract.Presenter {

    override fun loadAdminData() {
        // 1. Math Calculation
        val dist = model.getDistributedCount()
        val total = model.getTotalFamilies()
        val remaining = total - dist
        val percent = ((dist.toDouble() / total.toDouble()) * 100).toInt()

        view.updateProgress(percent, dist, remaining)

        // 2. Load DB Data
        view.displayInventory(model.getInventory())
        view.displayRecentDistributions(model.getRecentDistributions())
    }

    override fun onScannerTabClicked() {
        view.navigateToScanner()
    }

    override fun onInventoryTabClicked() {
        view.navigateToInventory()
    }

    override fun onHistoryTabClicked() {
        view.navigateToHistory()
    }


}