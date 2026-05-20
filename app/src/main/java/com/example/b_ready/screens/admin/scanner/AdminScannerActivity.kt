package com.example.b_ready.screens.admin.scanner

import android.app.Activity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.b_ready.R
import com.example.b_ready.db.DatabaseHelper
import com.example.b_ready.utils.toast
import com.google.android.material.button.MaterialButton

class AdminScannerActivity : Activity(), AdminScannerContract.View {

    private lateinit var presenter: AdminScannerPresenter
    private lateinit var etClaimId: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_scanner)

        etClaimId = findViewById(R.id.etClaimId)

        presenter = AdminScannerPresenter(this, AdminScannerModel(DatabaseHelper(this)))

        // Back Button
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        // Verify Button
        findViewById<MaterialButton>(R.id.btnVerify).setOnClickListener {
            val claimId = etClaimId.text.toString().trim()
            presenter.verifyClaim(claimId)
        }

        // Load initial data
        presenter.loadTodayStats()
    }

    override fun updateScanCount(count: Int) {
        findViewById<TextView>(R.id.tvScanCount).text = count.toString()
    }

    override fun showSuccess(message: String) {
        toast(message)
    }

    override fun showError(message: String) {
        toast(message)
    }

    override fun clearInput() {
        etClaimId.text.clear()
    }
}