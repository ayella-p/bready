package com.example.b_ready.screens.history

import com.example.b_ready.data.Transaction
import com.example.b_ready.db.DatabaseHelper

class HistoryModel(private val dbHelper: DatabaseHelper) {

    // This is the magic of MVP. The Presenter doesn't care where the data comes from.
    // We just swapped the dummy list for a real database call, and the Presenter won't even notice!
    fun getTransactions(): List<Transaction> {
        return dbHelper.getAllTransactions()
    }
}