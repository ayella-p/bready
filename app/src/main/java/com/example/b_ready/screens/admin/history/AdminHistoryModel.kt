package com.example.b_ready.screens.admin.history

import com.example.b_ready.data.Transaction
import com.example.b_ready.db.DatabaseHelper

class AdminHistoryModel(private val dbHelper: DatabaseHelper) {

    // Asks the database helper to get every transaction on record
    fun getAllTransactions(): List<Transaction> {
        return dbHelper.getAllTransactions()
    }
}