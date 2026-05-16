package com.example.b_ready.screens.history
class HistoryPresenter(
    private val view: HistoryContract.View,
    private val model: HistoryModel
) : HistoryContract.Presenter {
    override fun onBackClicked() {
        view.goBackToDashboard()
    }
    override fun onFilterClicked(filterType: String) {
        view.highlightFilterButton(filterType)

        view.showToastMessage("Showing transactions")
    }
    override fun onLoadMoreClicked() {
        view.showToastMessage("Loading older transactions...")
    }
    override fun onReceiptClicked() {
        view.showToastMessage("Downloading Receipt...")
    }
    fun loadHistoryData() {
        val transactions = model.getTransactions()
        view.displayTransactions(transactions)
    }
}