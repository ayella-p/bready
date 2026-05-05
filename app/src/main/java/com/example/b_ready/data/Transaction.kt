package com.example.b_ready.data

data class Transaction(
    val title: String,
    val date: String,
    val status: String,    // e.g., "Completed", "Paid", "Refunded"
    val price: String?,    // Nullable, because Relief packs don't have a price
    val isRelief: Boolean  // True for orange gift icon, False for blue box icon
)