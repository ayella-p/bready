package com.example.b_ready.utils

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.b_ready.R
import com.example.b_ready.data.InventoryItem

class InventoryAdapter(
    private var list: List<InventoryItem>,
    private val onUpdateClicked: (InventoryItem, Int, Boolean) -> Unit // Action listener!
) : RecyclerView.Adapter<InventoryAdapter.ViewHolder>() {

    fun updateData(newList: List<InventoryItem>) {
        list = newList
        notifyDataSetChanged() // Refreshes the list when DB updates
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvInvName)
        val qty: TextView = view.findViewById(R.id.tvInvQty)
        val unit: TextView = view.findViewById(R.id.tvInvUnit)
        val badgeText: TextView = view.findViewById(R.id.tvBadgeText)
        val badgeBg: CardView = view.findViewById(R.id.badgeBg)

        val layoutDefault: LinearLayout = view.findViewById(R.id.layoutDefault)
        val layoutEditing: LinearLayout = view.findViewById(R.id.layoutEditing)
        val etQty: EditText = view.findViewById(R.id.etQuantity)

        val btnInitAdd: Button = view.findViewById(R.id.btnInitAdd)
        val btnInitDeduct: Button = view.findViewById(R.id.btnInitDeduct)
        val btnConfirmAdd: Button = view.findViewById(R.id.btnConfirmAdd)
        val btnConfirmDeduct: Button = view.findViewById(R.id.btnConfirmDeduct)
        val btnCancel: Button = view.findViewById(R.id.btnCancel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_inventory_manage, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.name.text = item.name
        holder.qty.text = item.currentStock.toString()
        holder.unit.text = item.unit

        // Set Colors for Badge
        if (item.getStatus() == "Good Stock") {
            holder.badgeBg.setCardBackgroundColor(Color.parseColor("#E8F5E9"))
            holder.badgeText.setTextColor(Color.parseColor("#4CAF50"))
            holder.badgeText.text = "Good Stock"
        } else {
            holder.badgeBg.setCardBackgroundColor(Color.parseColor("#FFF9C4"))
            holder.badgeText.setTextColor(Color.parseColor("#FBC02D"))
            holder.badgeText.text = "Low Stock"
        }

        // Toggle UI based on editing state
        if (item.isEditing) {
            holder.layoutDefault.visibility = View.GONE
            holder.layoutEditing.visibility = View.VISIBLE
        } else {
            holder.layoutDefault.visibility = View.VISIBLE
            holder.layoutEditing.visibility = View.GONE
            holder.etQty.text.clear() // Clear text box when closed
        }

        // Click Listeners for state changes
        holder.btnInitAdd.setOnClickListener {
            item.isEditing = true
            notifyItemChanged(position)
        }
        holder.btnInitDeduct.setOnClickListener {
            item.isEditing = true
            notifyItemChanged(position)
        }
        holder.btnCancel.setOnClickListener {
            item.isEditing = false
            notifyItemChanged(position)
        }

        // Send data to Presenter to update Database!
        holder.btnConfirmAdd.setOnClickListener {
            val amount = holder.etQty.text.toString().toIntOrNull() ?: 0
            if (amount > 0) onUpdateClicked(item, amount, true)
        }

        holder.btnConfirmDeduct.setOnClickListener {
            val amount = holder.etQty.text.toString().toIntOrNull() ?: 0
            if (amount > 0) onUpdateClicked(item, amount, false)
        }
    }

    override fun getItemCount() = list.size
}