package com.example.b_ready.data

data class User(
    val username: String,
    val password: String,
    val role: String // "Admin" or "Resident"
)