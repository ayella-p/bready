package com.example.b_ready.screens.admin.inventory

import com.example.b_ready.data.InventoryItem
import com.example.b_ready.db.DatabaseHelper

class AdminInventoryModel(private val dbHelper: DatabaseHelper) {
    fun getInventory() = dbHelper.getInventory()

    fun updateStock(item: InventoryItem, amount: Int, isAdding: Boolean): Boolean {
        val newStock = if (isAdding) {
            item.currentStock + amount
        } else {
            item.currentStock - amount
        }

        if (newStock < 0) return false

        return dbHelper.updateInventoryStock(item.id, newStock)
    }
}