package com.nimmaguru.app.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.nimmaguru.app.FirebaseRepository
import com.nimmaguru.app.Guru
import com.nimmaguru.app.GuruAdapter
import com.nimmaguru.app.R

class SearchFragment : Fragment() {

    private lateinit var rvGurus: RecyclerView
    private lateinit var etSearch: EditText
    private lateinit var chipGroupFilter: ChipGroup
    private lateinit var adapter: GuruAdapter

    private val sampleGurus = listOf(
        Guru("Ramesh Kumar",  "Mysuru",    "9876543210", "Sat 10am-12pm", listOf("Mathematics", "Science")),
        Guru("Savitha Devi",  "Bengaluru", "9845612345", "Sun 9am-11am",  listOf("English", "Kannada")),
        Guru("Prakash Rao",   "Hubli",     "9731234567", "Sat 2pm-4pm",   listOf("Carpentry", "Computer")),
        Guru("Manjamma",      "Dharwad",   "9654321098", "Sun 10am-12pm", listOf("Mathematics", "English")),
        Guru("Venkatesh",     "Tumkur",    "9123456789", "Sat 11am-1pm",  listOf("Science", "Computer"))
    )

    private var allGurus = sampleGurus.toMutableList()
    private var filteredList = allGurus.toMutableList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        rvGurus         = view.findViewById(R.id.rvGurus)
        etSearch        = view.findViewById(R.id.etSearch)
        chipGroupFilter = view.findViewById(R.id.chipGroupFilter)

        adapter = GuruAdapter(filteredList)
        rvGurus.layoutManager = LinearLayoutManager(requireContext())
        rvGurus.adapter = adapter

        // Load from Firebase — falls back to sample if empty
        FirebaseRepository.getGurus { firebaseGurus ->
            if (firebaseGurus.isNotEmpty()) {
                allGurus = firebaseGurus.toMutableList()
            } else {
                allGurus = sampleGurus.toMutableList()
            }
            filteredList = allGurus.toMutableList()
            requireActivity().runOnUiThread {
                adapter.updateList(filteredList)
            }
        }

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { applyFilters() }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        chipGroupFilter.setOnCheckedStateChangeListener { _, _ -> applyFilters() }

        return view
    }

    private fun applyFilters() {
        val searchText = etSearch.text.toString().lowercase().trim()
        val checkedId  = chipGroupFilter.checkedChipId
        val selectedSkill = if (checkedId == View.NO_ID) "All" else {
            chipGroupFilter.findViewById<Chip>(checkedId)?.text?.toString() ?: "All"
        }

        filteredList = allGurus.filter { guru ->
            val matchSearch = searchText.isEmpty()
                    || guru.name.lowercase().contains(searchText)
                    || guru.village.lowercase().contains(searchText)

            // ✅ Handle both List and comma-separated string skills
            val matchSkill = selectedSkill == "All" || run {
                val skillStr = guru.skills.joinToString(",").lowercase()
                skillStr.contains(selectedSkill.lowercase())
            }

            matchSearch && matchSkill
        }.toMutableList()

        adapter.updateList(filteredList)
    }
}