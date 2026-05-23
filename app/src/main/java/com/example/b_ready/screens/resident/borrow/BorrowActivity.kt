package com.example.b_ready.screens.resident.borrow

import android.app.Activity
import android.content.ContentValues
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.b_ready.R
import com.example.b_ready.db.DatabaseHelper
import com.example.b_ready.utils.toast

class BorrowActivity : Activity(), BorrowContract.View {
    private lateinit var presenter: BorrowPresenter
    private lateinit var adapter: EquipmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resident_borrow)

        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }

        val rv = findViewById<RecyclerView>(R.id.rvEquipment)
        rv.layoutManager = LinearLayoutManager(this)
        adapter = EquipmentAdapter(emptyList()) { presenter.clickReserve(it) }
        rv.adapter = adapter

        presenter = BorrowPresenter(this, BorrowModel(DatabaseHelper(this)))
        presenter.loadItems()
    }
    override fun showEquipment(list: List<ContentValues>) { adapter.updateList(list) }
    override fun showStatus(msg: String) { toast(msg) }
}