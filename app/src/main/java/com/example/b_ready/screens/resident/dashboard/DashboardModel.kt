package com.example.b_ready.screens.resident.dashboard

import com.example.b_ready.app.CustomApp
import com.example.b_ready.data.User
import com.example.b_ready.db.DatabaseHelper

class DashboardModel(private val app: CustomApp, private val dbHelper: DatabaseHelper) {

    fun getCurrentUser(): User? {
        return app.getCurrentUser()
    }

    fun getWalletBalance(): String {
        val transactions = dbHelper.getAllTransactions()

        //example logic
        var netBalance = 1500.00
        for (tx in transactions) {
            if (tx.status == "Paid" && tx.price != null) {
                val numericPrice = tx.price.replace("₱", "").replace(",", "").toDoubleOrNull() ?: 0.0
                netBalance -= numericPrice
            }
        }
        return String.format("₱%,.2f", netBalance)
    }
    fun checkReliefClaimStatus(username: String): Boolean {
        val numericExtract = username.filter { it.isDigit() }
        val claimId = if (numericExtract.isNotEmpty()) numericExtract else "7777"
        return dbHelper.isResidentIdClaimed(claimId)
    }
}