package com.example.b_ready.screens.resident.history

import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import com.example.b_ready.R
import com.example.b_ready.data.Transaction
import com.example.b_ready.db.DatabaseHelper
import com.example.b_ready.utils.TransactionAdapter
import com.google.android.material.button.MaterialButton

class HistoryActivity : Activity(), HistoryContract.View {

    private lateinit var presenter: HistoryPresenter
    private lateinit var btnFilterAll: MaterialButton
    private lateinit var btnFilterRelief: MaterialButton
    private lateinit var btnFilterBookings: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resident_history)

        val model = HistoryModel(DatabaseHelper(this))
        presenter = HistoryPresenter(this, model)

        btnFilterAll = findViewById<MaterialButton>(R.id.btnFilterAll)
        btnFilterRelief = findViewById(R.id.btnFilterRelief)
        btnFilterBookings = findViewById(R.id.btnFilterBookings)

        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }

        btnFilterAll.setOnClickListener { presenter.onFilterClicked("All") }
        btnFilterRelief.setOnClickListener { presenter.onFilterClicked("Relief") }
        btnFilterBookings.setOnClickListener { presenter.onFilterClicked("Bookings") }

        presenter.loadHistory()
    }

    override fun displayTransactions(transactions: List<Transaction>) {
        val listView = findViewById<ListView>(R.id.listViewHistory) // Match your layout ListView ID
        val adapter = TransactionAdapter(this, transactions)
        listView.adapter = adapter
    }

    override fun updateFilterUI(activeFilter: String) {
        val activeBg = ColorStateList.valueOf(Color.parseColor("#1B3A6D"))
        val inactiveBg = ColorStateList.valueOf(Color.WHITE)
        val activeText = Color.WHITE
        val inactiveText = Color.parseColor("#1B3A6D")

        // Reset
        listOf(btnFilterAll, btnFilterRelief, btnFilterBookings).forEach {
            it.backgroundTintList = inactiveBg
            it.setTextColor(inactiveText)
        }

        // Apply active highlight styling
        when (activeFilter) {
            "All" -> {
                btnFilterAll.backgroundTintList = activeBg
                btnFilterAll.setTextColor(activeText)
            }
            "Relief" -> {
                btnFilterRelief.backgroundTintList = activeBg
                btnFilterRelief.setTextColor(activeText)
            }
            "Bookings" -> {
                btnFilterBookings.backgroundTintList = activeBg
                btnFilterBookings.setTextColor(activeText)
            }
        }
    }
}