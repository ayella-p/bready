package com.example.b_ready.screens.dashboard


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