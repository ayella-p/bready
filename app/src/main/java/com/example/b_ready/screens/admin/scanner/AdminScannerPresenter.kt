package com.example.b_ready.screens.admin.scanner

class AdminScannerPresenter(
    private val view: AdminScannerContract.View,
    private val model: AdminScannerModel
) : AdminScannerContract.Presenter {

    override fun loadTodayStats() {
        val count = model.getTodayScans()
        view.updateScanCount(count)
    }

    override fun verifyClaim(claimId: String) {
        if (claimId.isEmpty()) {
            view.showError("Please enter a Claim ID")
            return
        }

        val isSuccess = model.processVerification(claimId)

        if (isSuccess) {
            view.showSuccess("Claim $claimId verified successfully!")
            view.clearInput()
            loadTodayStats() // Refresh the number on the screen
        } else {
            view.showError("Invalid Claim ID. Please try again.")
        }
    }
}