package com.example.b_ready.data

data class InventoryItem(
    val id: Int, // Added ID for database updates
    val name: String,
    val currentStock: Int,
    val maxStock: Int,
    val unit: String, // "bags", "pcs", etc.
    var isEditing: Boolean = false // UI trick: tracks if edit mode is open!
) {
    fun getProgressPercentage(): Int {
        return ((currentStock.toDouble() / maxStock.toDouble()) * 100).toInt()
    }

    fun getStatus(): String {
        return if (currentStock <= maxStock * 0.3) "Low Stock" else "Good Stock"
    }
}