package com.example.b_ready.screens.resident.claim

import com.example.b_ready.app.CustomApp
import com.example.b_ready.data.User

class ResidentClaimModel(private val app: CustomApp) {

    fun getSessionUser(): User? {
        return app.getCurrentUser()
    }

    fun getFormattedClaimId(username: String): String {
        val numericExtract = username.filter { it.isDigit() }
        val fallbackDigits = if (numericExtract.isNotEmpty()) numericExtract else "7777"

        return "BR-2026-$fallbackDigits"
    }
}