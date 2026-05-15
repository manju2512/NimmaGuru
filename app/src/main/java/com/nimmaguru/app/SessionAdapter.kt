package com.nimmaguru.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Session(
    val subject: String = "",
    val guruName: String = "",
    val time: String = "",
    val venue: String = "",
    val date: String = ""
)

class SessionAdapter(private var sessionList: List<Session>) :
    RecyclerView.Adapter<SessionAdapter.SessionViewHolder>() {

    class SessionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSubject: TextView  = itemView.findViewById(R.id.tvSubject)
        val tvGuruName: TextView = itemView.findViewById(R.id.tvSessionGuru)
        val tvTime: TextView     = itemView.findViewById(R.id.tvSessionTime)
        val tvVenue: TextView    = itemView.findViewById(R.id.tvSessionVenue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_session_card, parent, false)
        return SessionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        val session = sessionList[position]
        holder.tvSubject.text  = session.subject
        holder.tvGuruName.text = "Guru: ${session.guruName}"
        holder.tvTime.text     = "Time: ${session.time}"
        holder.tvVenue.text    = "Venue: ${session.venue}"
    }

    override fun getItemCount() = sessionList.size

    fun updateList(newList: List<Session>) {
        sessionList = newList
        notifyDataSetChanged()
    }
}