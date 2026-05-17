package com.example.b_ready.screens.admin.inventory

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.b_ready.R
import com.example.b_ready.data.InventoryItem
import com.example.b_ready.db.DatabaseHelper
import com.example.b_ready.utils.InventoryAdapter
import com.example.b_ready.utils.toast

class AdminInventoryActivity : Activity(), AdminInventoryContract.View {

    private lateinit var presenter: AdminInventoryPresenter
    private lateinit var adapter: InventoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_inventory)

        presenter = AdminInventoryPresenter(this, AdminInventoryModel(DatabaseHelper(this)))

        // Setup Back Button
        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }

        // Setup RecyclerView
        val rv = findViewById<RecyclerView>(R.id.recyclerViewInventory)
        rv.layoutManager = LinearLayoutManager(this)

        // Pass the listener to the adapter
        adapter = InventoryAdapter(emptyList()) { item, amount, isAdding ->
            presenter.processStockUpdate(item, amount, isAdding)
        }
        rv.adapter = adapter

        // Load Data!
        presenter.loadInventory()
    }

    override fun displayInventory(items: List<InventoryItem>) {
        adapter.updateData(items) // Sends fresh DB data to UI
    }

    override fun showMessage(msg: String) {
        toast(msg)
    }
}