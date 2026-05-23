package com.example.b_ready.screens.resident.profile

class ProfilePresenter(
    private val view: ProfileContract.View,
    private val model: ProfileModel
) : ProfileContract.Presenter {

    override fun loadUserProfile() {
        val user = model.getActiveUser()
        val name = user?.username ?: "Unknown User"
        val role = user?.role ?: "Resident"
        view.displayUserInfo(name, role)
    }

    override fun handleLogout() {
        model.clearUserSession()
        view.navigateToLogin()
    }
}