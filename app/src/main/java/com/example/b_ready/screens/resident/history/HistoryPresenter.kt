package com.example.b_ready.screens.resident.history

class HistoryPresenter(
    private val view: HistoryContract.View,
    private val model: HistoryModel
) : HistoryContract.Presenter {

    private var currentFilter = "All"

    override fun loadHistory() {
        onFilterClicked(currentFilter)
    }

    override fun onFilterClicked(filterType: String) {
        currentFilter = filterType
        val allTransactions = model.getAllTransactions()

        val filteredList = when (filterType) {
            "Relief" -> allTransactions.filter { it.isRelief == true }
            "Bookings" -> allTransactions.filter { it.isRelief == false }
            else -> allTransactions
        }

        view.displayTransactions(filteredList)
        view.updateFilterUI(filterType)
    }
}