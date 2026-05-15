package com.nimmaguru.app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nimmaguru.app.R
import com.nimmaguru.app.Session
import com.nimmaguru.app.SessionAdapter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CalendarFragment : Fragment() {

    private lateinit var calendarView: CalendarView
    private lateinit var tvSelectedDate: TextView
    private lateinit var tvNoSessions: TextView
    private lateinit var rvSessions: RecyclerView
    private lateinit var adapter: SessionAdapter

    private val allSessions = listOf(
        Session("Mathematics", "Ramesh Kumar", "10:00 AM - 12:00 PM", "Samudaya Bhavana, Mysuru",    "2026-05-16"),
        Session("Science",     "Venkatesh",    "11:00 AM - 1:00 PM",  "Samudaya Bhavana, Tumkur",    "2026-05-16"),
        Session("English",     "Savitha Devi", "9:00 AM - 11:00 AM",  "Samudaya Bhavana, Bengaluru", "2026-05-17"),
        Session("Carpentry",   "Prakash Rao",  "2:00 PM - 4:00 PM",   "Samudaya Bhavana, Hubli",     "2026-05-17"),
        Session("Kannada",     "Manjamma",     "10:00 AM - 12:00 PM", "Samudaya Bhavana, Dharwad",   "2026-05-18"),
        Session("Computer",    "Venkatesh",    "3:00 PM - 5:00 PM",   "Samudaya Bhavana, Tumkur",    "2026-05-18"),
        Session("Mathematics", "Ramesh Kumar", "10:00 AM - 12:00 PM", "Samudaya Bhavana, Mysuru",    "2026-05-23"),
        Session("Science",     "Savitha Devi", "11:00 AM - 1:00 PM",  "Samudaya Bhavana, Bengaluru", "2026-05-24")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        calendarView   = view.findViewById(R.id.calendarView)
        tvSelectedDate = view.findViewById(R.id.tvSelectedDate)
        tvNoSessions   = view.findViewById(R.id.tvNoSessions)
        rvSessions     = view.findViewById(R.id.rvSessions)

        adapter = SessionAdapter(emptyList())
        rvSessions.layoutManager = LinearLayoutManager(requireContext())
        rvSessions.adapter = adapter

        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        tvSelectedDate.text = "Sessions on: $today"
        showSessionsForDate(today)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selected = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            tvSelectedDate.text = "Sessions on: $selected"
            showSessionsForDate(selected)
        }

        return view
    }

    private fun showSessionsForDate(date: String) {
        val filtered = allSessions.filter { it.date == date }
        if (filtered.isEmpty()) {
            tvNoSessions.visibility = View.VISIBLE
            rvSessions.visibility   = View.GONE
        } else {
            tvNoSessions.visibility = View.GONE
            rvSessions.visibility   = View.VISIBLE
            adapter.updateList(filtered)
        }
    }
}