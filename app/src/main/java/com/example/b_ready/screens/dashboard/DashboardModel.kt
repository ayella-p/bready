package com.example.b_ready.screens.dashboard

import com.example.b_ready.app.CustomApp
import com.example.b_ready.data.User

class DashboardModel(private val app: CustomApp) {

    fun getCurrentUser(): User {
        return app.getUser()
    }

    fun getWalletBalance(): String {
        return "₱1,250.00"
    }
}