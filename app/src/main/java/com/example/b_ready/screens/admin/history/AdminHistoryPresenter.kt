package com.example.b_ready.screens.admin.history

class AdminHistoryPresenter(
    private val view: AdminHistoryContract.View,
    private val model: AdminHistoryModel
) : AdminHistoryContract.Presenter {

    // Keep track of the current filter so if we refresh, it stays on the same tab
    private var currentFilter = "All"

    override fun loadHistory() {
        // Trigger the filter logic to load the list
        onFilterClicked(currentFilter)
    }

    override fun onFilterClicked(filterType: String) {
        currentFilter = filterType

        // 1. Get ALL transactions from the database
        val allTransactions = model.getAllTransactions()

        // 2. Filter the list based on what was clicked
        val filteredList = when (filterType) {
            "Relief" -> allTransactions.filter { it.isRelief == true }
            "Bookings" -> allTransactions.filter { it.isRelief == false }
            else -> allTransactions // "All" just returns everything
        }

        // 3. Send the filtered list to the screen
        view.displayTransactions(filteredList)

        // 4. Tell the screen to highlight the correct button
        view.updateFilterUI(filterType)
    }

    override fun onBackButtonClicked() {
        view.navigateToDashboard()
    }
}