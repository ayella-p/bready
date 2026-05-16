package com.example.b_ready.utils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.b_ready.R
import com.example.b_ready.data.RecentDistribution

class RecentDistributionAdapter(private val list: List<RecentDistribution>) : RecyclerView.Adapter<RecentDistributionAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvDistName)
        val details: TextView = view.findViewById(R.id.tvDistDetails)
        val status: TextView = view.findViewById(R.id.tvDistStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recent_distribution, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.name.text = item.residentName
        holder.details.text = item.transactionDetails
        holder.status.text = item.status
    }

    override fun getItemCount() = list.size
}