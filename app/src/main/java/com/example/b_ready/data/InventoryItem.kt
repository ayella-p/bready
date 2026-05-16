package com.example.b_ready.data

// For the Stock Levels
data class InventoryItem(
    val name: String,
    val currentStock: Int,
    val maxStock: Int
) {
    // Accurate calculation for the progress bar
    fun getProgressPercentage(): Int {
        return ((currentStock.toDouble() / maxStock.toDouble()) * 100).toInt()
    }
}