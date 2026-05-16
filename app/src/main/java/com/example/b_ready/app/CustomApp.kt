package com.example.b_ready.app

import android.app.Application
import android.util.Log
import com.example.b_ready.data.User

class CustomApp : Application() {
    // This starts as null. It only gets filled when someone successfully logs in.
    private var currentUser: User? = null

    override fun onCreate() {
        super.onCreate()
        Log.e("B-Ready", "CustomApp Started")
    }

    // call this when they log in
    fun setCurrentUser(user: User) {
        this.currentUser = user
    }

    // call this from the Dashboard to know who is logged in
    fun getCurrentUser(): User? {
        return this.currentUser
    }

    // call this for Logout
    fun clearUser() {
        this.currentUser = null
    }
}