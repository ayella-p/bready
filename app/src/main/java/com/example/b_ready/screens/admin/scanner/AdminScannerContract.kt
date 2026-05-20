package com.example.b_ready.screens.admin.scanner

class AdminScannerContract {
    interface View {
        fun updateScanCount(count: Int)
        fun showSuccess(message: String)
        fun showError(message: String)
        fun clearInput()
    }

    interface Presenter {
        fun loadTodayStats()
        fun verifyClaim(claimId: String)
    }
}