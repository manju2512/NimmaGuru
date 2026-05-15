package com.nimmaguru.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Appreciation(
    val studentName: String = "",
    val guruName: String = "",
    val message: String = "",
    val date: String = ""
)

class AppreciationAdapter(private var list: List<Appreciation>) :
    RecyclerView.Adapter<AppreciationAdapter.AppreciationViewHolder>() {

    class AppreciationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvStudentName: TextView = itemView.findViewById(R.id.tvStudentName)
        val tvGuruName: TextView    = itemView.findViewById(R.id.tvAppGuruName)
        val tvMessage: TextView     = itemView.findViewById(R.id.tvMessage)
        val tvDate: TextView        = itemView.findViewById(R.id.tvDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppreciationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_appreciation_card, parent, false)
        return AppreciationViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppreciationViewHolder, position: Int) {
        val item = list[position]
        holder.tvStudentName.text = item.studentName
        holder.tvGuruName.text    = "To: ${item.guruName}"
        holder.tvMessage.text     = item.message
        holder.tvDate.text        = item.date
    }

    override fun getItemCount() = list.size

    fun updateList(newList: List<Appreciation>) {
        list = newList
        notifyDataSetChanged()
    }
}