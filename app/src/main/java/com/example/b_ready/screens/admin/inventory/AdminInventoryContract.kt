package com.example.b_ready.screens.admin.inventory

import com.example.b_ready.data.InventoryItem

class AdminInventoryContract {
    interface View {
        fun displayInventory(items: List<InventoryItem>)
        fun showMessage(msg: String)
    }
    interface Presenter {
        fun loadInventory()
        fun processStockUpdate(item: InventoryItem, amount: Int, isAdding: Boolean)
    }
}