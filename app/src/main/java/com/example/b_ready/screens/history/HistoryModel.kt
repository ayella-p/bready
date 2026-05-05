package com.example.b_ready.screens.history

import com.example.b_ready.data.Transaction

class HistoryModel {
    fun fetchTransactions() {
        // database fetch
    }
    fun getDummyTransactions(): List<Transaction> {
        return listOf(
            Transaction("Relief Pack Claimed", "April 8, 2026 • 2:45 PM", "Completed", null, true),
            Transaction("Tent Booking Payment", "April 5, 2026 • 10:30 AM", "Paid", "₱100", false),
            Transaction("Relief Pack Claimed", "March 28, 2026 • 3:15 PM", "Completed", null, true),
            Transaction("Chairs Booking Deposit", "March 20, 2026 • 11:00 AM", "Refunded", "₱200", false),
            Transaction("Emergency Food Package", "March 15, 2026 • 9:20 AM", "Completed", null, true),
            Transaction("Sound System Deposit", "March 10, 2026 • 2:00 PM", "Refunded", "₱1000", false)
        )
    }
}