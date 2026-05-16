package com.example.b_ready.screens.resident.dashboard

import com.example.b_ready.app.CustomApp

class DashboardPresenter(
    private val view: DashboardContract.View,
    private val model: DashboardModel
) : DashboardContract.Presenter {

    override fun loadDashboardData() {
        val currentUser = model.getCurrentUser()
        val currentBalance = model.getWalletBalance()
        val displayName = currentUser?.username ?: "Guest"
        view.displayWalletBalance(currentBalance)
    }


    override fun onHistoryTabClicked() {
        view.navigateToHistory()
    }

    override fun onProfileTabClicked() {
        view.navigateToProfile()
    }

    override fun onClaimButtonClicked() {
        view.showToastMessage("Preparing Relief Claim QR...")
        view.showClaimScreen()
    }

    override fun onRequestAidClicked() {
        view.showRequestAidScreen()
    }

    override fun onBorrowEquipmentClicked() {
        view.showBorrowEquipmentScreen()
    }
}