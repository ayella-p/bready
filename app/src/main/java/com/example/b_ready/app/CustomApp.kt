package com.example.b_ready.app

import android.app.Application
import android.util.Log
import com.example.b_ready.data.User

class CustomApp : Application() {
    // Hardcoded test user based on your UI mockup
    private val testUser = User("09123456789", "1234")

    override fun onCreate() {
        super.onCreate()
        Log.e("B-Ready", "CustomApp onCreate is called - App Started")
    }

    fun getUser(): User {
        return this.testUser
    }
}