package com.example.b_ready.screens.resident.history

import com.example.b_ready.data.Transaction

class HistoryContract {
    interface View {
        fun displayTransactions(transactions: List<Transaction>)
        fun updateFilterUI(activeFilter: String)
    }

    interface Presenter {
        fun loadHistory()
        fun onFilterClicked(filterType: String)
    }
}