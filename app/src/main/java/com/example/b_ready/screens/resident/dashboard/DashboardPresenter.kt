package com.example.b_ready.screens.resident.dashboard

class DashboardPresenter(
    private val view: DashboardContract.View,
    private val model: DashboardModel
) : DashboardContract.Presenter {



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
    override fun loadDashboardData() {
        val currentUser = model.getCurrentUser()
        val currentBalance = model.getWalletBalance()
        val displayName = currentUser?.username ?: "Guest"

        view.displayUserName(displayName)
        view.displayWalletBalance(currentBalance)

        if (currentUser != null && model.checkReliefClaimStatus(currentUser.username)) {
            view.setReliefCardToClaimedState()
        } else {
            view.setReliefCardToDefaultState() // 💡 NEW: Ensures unverified users see orange
        }
    }
}