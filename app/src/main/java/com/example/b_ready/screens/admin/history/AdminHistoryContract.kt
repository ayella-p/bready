package com.example.b_ready.screens.admin.history

import com.example.b_ready.data.Transaction

class AdminHistoryContract {
    interface View {
        fun displayTransactions(transactions: List<Transaction>)
        fun showToast(message: String)
        fun updateFilterUI(activeFilter: String) // NEW: Changes button colors!
        fun navigateToDashboard()
    }

    interface Presenter {
        fun loadHistory()
        fun onFilterClicked(filterType: String)
        fun onBackButtonClicked()
    }
}