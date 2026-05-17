package com.example.b_ready.screens.admin.inventory

import com.example.b_ready.data.InventoryItem

class AdminInventoryPresenter(
    private val view: AdminInventoryContract.View,
    private val model: AdminInventoryModel
) : AdminInventoryContract.Presenter {

    override fun loadInventory() {
        view.displayInventory(model.getInventory())
    }

    override fun processStockUpdate(item: InventoryItem, amount: Int, isAdding: Boolean) {
        val success = model.updateStock(item, amount, isAdding)

        if (success) {
            view.showMessage("Stock updated successfully!")
            loadInventory() // Reloads the fresh data from the DB!
        } else {
            view.showMessage("Error: Cannot deduct more than current stock.")
        }
    }
}