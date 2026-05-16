package com.example.b_ready.screens.resident.history

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.example.b_ready.R
import com.example.b_ready.data.Transaction
import com.example.b_ready.db.DatabaseHelper
import com.example.b_ready.utils.TransactionAdapter
import com.example.b_ready.utils.toast

class HistoryActivity : Activity(), HistoryContract.View {

    private lateinit var presenter: HistoryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resident_history)
        val dbHelper = DatabaseHelper(this)
        presenter = HistoryPresenter(this, HistoryModel(dbHelper))
        presenter.loadHistoryData()
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            presenter.onBackClicked()
        }

        findViewById<Button>(R.id.btnFilterAll).setOnClickListener {
            presenter.onFilterClicked("All")
        }
        findViewById<Button>(R.id.btnFilterRelief).setOnClickListener {
            presenter.onFilterClicked("Relief")
        }
        findViewById<Button>(R.id.btnFilterBookings).setOnClickListener {
            presenter.onFilterClicked("Bookings")
        }

        findViewById<TextView>(R.id.tvLoadMore).setOnClickListener {
            presenter.onLoadMoreClicked()
        }
    }

    override fun goBackToDashboard() {
        finish()
    }

    override fun showToastMessage(message: String) {
        toast(message)
    }

    override fun highlightFilterButton(filterType: String) {
    }
    override fun displayTransactions(transactions: List<Transaction>) {
        val listView = findViewById<ListView>(R.id.listViewHistory)
        val adapter = TransactionAdapter(this, transactions)
        listView.adapter = adapter
    }
}