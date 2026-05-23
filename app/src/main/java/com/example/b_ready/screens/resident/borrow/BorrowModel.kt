package com.example.b_ready.screens.resident.borrow

import com.example.b_ready.db.DatabaseHelper


class BorrowModel(private val dbHelper: DatabaseHelper) {
    fun fetchItems() = dbHelper.getAvailableEquipment()
    fun performBooking(id: Int, name: String, deposit: String): Boolean {
        val sdf = java.text.SimpleDateFormat("MMMM d, yyyy • h:mm a", java.util.Locale.getDefault())
        return dbHelper.reserveEquipmentItem(id, name, deposit, sdf.format(java.util.Date()))
    }
}