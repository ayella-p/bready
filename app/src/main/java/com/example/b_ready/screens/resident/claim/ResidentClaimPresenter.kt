package com.example.b_ready.screens.resident.claim

import com.example.b_ready.db.DatabaseHelper

class ResidentClaimPresenter(
    private val view: ResidentClaimContract.View,
    private val model: ResidentClaimModel,
    private val dbHelper: DatabaseHelper // ADDED
) : ResidentClaimContract.Presenter {

    override fun generateResidentClaimDetails() {
        val user = model.getSessionUser()

        if (user != null) {
            val generatedStringId = model.getFormattedClaimId(user.username)
            view.displayClaimId(generatedStringId)

            // 💡 NEW CHECK: Query database logs
            if (dbHelper.isResidentIdClaimed(generatedStringId)) {
                view.showAsClaimed()
            }
        } else {
            view.showErrorState()
        }
    }
}