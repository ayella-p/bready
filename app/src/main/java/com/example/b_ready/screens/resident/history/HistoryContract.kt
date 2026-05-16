package com.example.b_ready.screens.resident.history

import com.example.b_ready.data.Transaction

class HistoryContract {
    interface View {
        fun displayTransactions(transactions: List<Transaction>)
        fun goBackToDashboard()
        fun showToastMessage(message: String)
        fun highlightFilterButton(filterType: String)
    }

    interface Presenter {
        fun onBackClicked()
        fun onFilterClicked(filterType: String)
        fun onLoadMoreClicked()
        fun onReceiptClicked()
    }
}