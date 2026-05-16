package com.example.b_ready.data

data class RecentDistribution(
    val residentName: String,
    val transactionDetails: String, // e.g., "BR 2026 1234 • 2:45 PM"
    val status: String // e.g., "Verified"
)