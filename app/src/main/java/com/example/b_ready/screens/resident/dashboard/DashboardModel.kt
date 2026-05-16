package com.example.b_ready.screens.resident.dashboard

import com.example.b_ready.app.CustomApp
import com.example.b_ready.data.User

class DashboardModel(private val app: CustomApp) {

    fun getCurrentUser(): User? {
        return app.getCurrentUser()
    }

    fun getWalletBalance(): String {
        return "₱1,250.00"
    }
}