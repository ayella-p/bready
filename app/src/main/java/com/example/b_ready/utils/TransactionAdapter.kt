package com.example.b_ready.utils // Or wherever you put your adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.b_ready.R
import com.example.b_ready.data.Transaction

class TransactionAdapter(
    private val context: Context,
    private val transactionList: List<Transaction>
) : BaseAdapter() {

    override fun getCount(): Int = transactionList.size
    override fun getItem(position: Int): Any = transactionList[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_transaction, parent, false)

        // Find the views in our item_transaction.xml
        val tvTitle = view.findViewById<TextView>(R.id.tvTransTitle)
        val tvDate = view.findViewById<TextView>(R.id.tvTransDate)
        val tvPrice = view.findViewById<TextView>(R.id.tvTransPrice)
        val tvStatus = view.findViewById<TextView>(R.id.tvTransStatus)
        val cardStatusBg = view.findViewById<CardView>(R.id.cardStatusBg)
        val cardIconBg = view.findViewById<CardView>(R.id.cardIconBg)
        val ivIcon = view.findViewById<ImageView>(R.id.ivIcon)

        // Get the current transaction
        val transaction = transactionList[position]

        // 1. Set Basic Text
        tvTitle.text = transaction.title
        tvDate.text = transaction.date
        tvStatus.text = transaction.status

        // 2. Handle Price (Hide if null, Show if it has a value)
        if (transaction.price != null) {
            tvPrice.text = transaction.price
            tvPrice.visibility = View.VISIBLE
        } else {
            tvPrice.visibility = View.GONE
        }

        // 3. Handle Colors for Status Badge
        when (transaction.status) {
            "Completed" -> {
                cardStatusBg.setCardBackgroundColor(Color.parseColor("#E8F5E9")) // Light Green
                tvStatus.setTextColor(Color.parseColor("#4CAF50"))
            }
            "Paid" -> {
                cardStatusBg.setCardBackgroundColor(Color.parseColor("#E3F2FD")) // Light Blue
                tvStatus.setTextColor(Color.parseColor("#2196F3"))
            }
            "Refunded" -> {
                cardStatusBg.setCardBackgroundColor(Color.parseColor("#F1F5F9")) // Light Gray
                tvStatus.setTextColor(Color.parseColor("#64748B"))
            }
        }

        // 4. Handle Icon Types (Relief vs Booking)
        if (transaction.isRelief) {
            cardIconBg.setCardBackgroundColor(Color.parseColor("#FFF3E0")) // Orange Bg
            ivIcon.setImageResource(android.R.drawable.ic_menu_save) // Replace with your actual gift icon later
            ivIcon.setColorFilter(Color.parseColor("#FF7043"))
        } else {
            cardIconBg.setCardBackgroundColor(Color.parseColor("#F0F4F8")) // Blue Bg
            ivIcon.setImageResource(android.R.drawable.ic_menu_camera) // Replace with your actual box icon later
            ivIcon.setColorFilter(Color.parseColor("#1B3A6D"))
        }

        return view
    }
}