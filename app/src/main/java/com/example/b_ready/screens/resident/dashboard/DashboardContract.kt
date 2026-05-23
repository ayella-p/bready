package com.example.b_ready.screens.resident.dashboard

class DashboardContract {
    interface View {
        fun displayUserName(name: String)
        fun displayWalletBalance(balance: String)
        fun showToastMessage(message: String)

        fun navigateToHistory()
        fun navigateToProfile()
        fun showClaimScreen()
        fun showRequestAidScreen()
        fun showBorrowEquipmentScreen()

        // 💡 ADDED THIS LINE TO FIX THE COMPILER ERRORS:
        fun setReliefCardToClaimedState()
        fun setReliefCardToDefaultState()
    }

    interface Presenter {
        fun loadDashboardData()
        fun onHistoryTabClicked()
        fun onProfileTabClicked()
        fun onClaimButtonClicked()
        fun onRequestAidClicked()
        fun onBorrowEquipmentClicked()

    }
}