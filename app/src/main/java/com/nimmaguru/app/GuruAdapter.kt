package com.nimmaguru.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Guru(
    val name: String = "",
    val village: String = "",
    val phone: String = "",
    val freeHours: String = "",
    val skills: List<String> = emptyList()
)

class GuruAdapter(private var guruList: List<Guru>) :
    RecyclerView.Adapter<GuruAdapter.GuruViewHolder>() {

    class GuruViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvGuruName)
        val tvSkills: TextView = itemView.findViewById(R.id.tvGuruSkills)
        val tvVillage: TextView = itemView.findViewById(R.id.tvGuruVillage)
        val tvPhone: TextView = itemView.findViewById(R.id.tvGuruPhone)
        val tvHours: TextView = itemView.findViewById(R.id.tvGuruHours)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuruViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_guru_card, parent, false)
        return GuruViewHolder(view)
    }

    override fun onBindViewHolder(holder: GuruViewHolder, position: Int) {
        val guru = guruList[position]
        holder.tvName.text = guru.name
        holder.tvSkills.text = "Skills: ${guru.skills.joinToString(", ")}"
        holder.tvVillage.text = "Village: ${guru.village}"
        holder.tvPhone.text = "Phone: ${guru.phone}"
        holder.tvHours.text = "Hours: ${guru.freeHours}"
    }

    override fun getItemCount() = guruList.size

    fun updateList(newList: List<Guru>) {
        guruList = newList
        notifyDataSetChanged()
    }
}