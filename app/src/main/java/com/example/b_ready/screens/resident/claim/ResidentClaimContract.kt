package com.example.b_ready.screens.resident.claim

class ResidentClaimContract {
    interface View {
        fun displayClaimId(formattedId: String)
        fun showErrorState()
        fun showAsClaimed()
    }
    interface Presenter {
        fun generateResidentClaimDetails()
    }
}