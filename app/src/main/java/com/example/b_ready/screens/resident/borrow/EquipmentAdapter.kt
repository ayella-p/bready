package com.example.b_ready.screens.resident.borrow

import android.content.ContentValues
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.b_ready.R
import com.google.android.material.button.MaterialButton

class EquipmentAdapter(
    private var items: List<ContentValues>,
    private val onReserveClick: (ContentValues) -> Unit
) : RecyclerView.Adapter<EquipmentAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val name: TextView = v.findViewById(R.id.tvEqName)
        val deposit: TextView = v.findViewById(R.id.tvEqDeposit)
        val available: TextView = v.findViewById(R.id.tvEqAvailable)
        val btnReserve: MaterialButton = v.findViewById(R.id.btnReserve)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_equipment, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val nameStr = item.getAsString("name")
        val depositStr = item.getAsString("deposit")
        val count = item.getAsInteger("available")

        holder.name.text = nameStr
        holder.deposit.text = "Deposit: $depositStr"
        holder.available.text = "Available: $count units"

        if (count <= 0) {
            holder.btnReserve.isEnabled = false
            holder.btnReserve.text = "Out of Stock"
        } else {
            holder.btnReserve.isEnabled = true
            holder.btnReserve.text = "Reserve"
        }

        holder.btnReserve.setOnClickListener { onReserveClick(item) }
    }

    override fun getItemCount() = items.size

    fun updateList(newList: List<ContentValues>) {
        items = newList
        notifyDataSetChanged()
    }
}