package com.example.b_ready.screens.admin.scanner

import com.example.b_ready.db.DatabaseHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AdminScannerModel(private val dbHelper: DatabaseHelper) {

    // Pulls the true total number of rows currently saved in the DB
    fun getTodayScans(): Int {
        return dbHelper.getTodayVerificationCount()
    }

    fun processVerification(claimId: String): Boolean {
        if (claimId.isEmpty()) return false

        // 1. Format the time automatically (e.g., "2:45 PM")
        val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
        val currentTime = timeFormat.format(Date())

        // 2. Standardize the format to look like your design: "BR-2026-1234 • 2:45 PM"
        val formattedId = if (claimId.uppercase().startsWith("BR-")) claimId.uppercase() else "BR-2026-$claimId"
        val displayDetails = "$formattedId • $currentTime"

        // 3. For MVP presentation, simulate a resident's name based on the ID input
        val realResidentName = dbHelper.getResidentNameByCode(claimId)

        // 4. Save directly into the shared database table!
        return dbHelper.insertDistribution(
            residentName = realResidentName,
            details = displayDetails,
            status = "Verified"
        )
    }
}