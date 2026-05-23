package com.example.b_ready.screens.resident.profile

class ProfileContract {
    interface View {
        fun displayUserInfo(username: String, role: String)
        fun navigateToLogin()
    }
    interface Presenter {
        fun loadUserProfile()
        fun handleLogout()
    }
}