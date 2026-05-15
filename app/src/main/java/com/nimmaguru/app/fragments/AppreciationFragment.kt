package com.nimmaguru.app.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nimmaguru.app.Appreciation
import com.nimmaguru.app.AppreciationAdapter
import com.nimmaguru.app.FirebaseRepository
import com.nimmaguru.app.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AppreciationFragment : Fragment() {

    private lateinit var rvAppreciation: RecyclerView
    private lateinit var fabAddNote: FloatingActionButton
    private lateinit var adapter: AppreciationAdapter

    private val guruNames = listOf(
        "Ramesh Kumar", "Savitha Devi",
        "Prakash Rao", "Manjamma", "Venkatesh"
    )

    private val appreciationList = mutableListOf(
        Appreciation("Arjun S", "Ramesh Kumar",
            "Your teaching style is amazing! I finally understood algebra.", "2026-05-10"),
        Appreciation("Priya K", "Savitha Devi",
            "Thank you for your patience in teaching English grammar.", "2026-05-11"),
        Appreciation("Kiran M", "Venkatesh",
            "The computer classes are very helpful for my future career.", "2026-05-11"),
        Appreciation("Suma R", "Manjamma",
            "Kannada classes helped me connect with my roots. Thank you!", "2026-05-12"),
        Appreciation("Rahul P", "Prakash Rao",
            "Carpentry skills will help me a lot. Grateful for your time.", "2026-05-12")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_appreciation, container, false)

        rvAppreciation = view.findViewById(R.id.rvAppreciation)
        fabAddNote     = view.findViewById(R.id.fabAddNote)

        adapter = AppreciationAdapter(appreciationList)
        rvAppreciation.layoutManager = LinearLayoutManager(requireContext())
        rvAppreciation.adapter = adapter

        // ✅ Load appreciations from Firebase
        FirebaseRepository.getAppreciations { firebaseList ->
            if (firebaseList.isNotEmpty()) {
                appreciationList.clear()
                appreciationList.addAll(firebaseList)
                adapter.updateList(appreciationList)
            }
        }

        fabAddNote.setOnClickListener { showAddNoteDialog() }

        return view
    }

    private fun showAddNoteDialog() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_add_appreciation, null)

        val etStudentName = dialogView.findViewById<EditText>(R.id.etStudentName)
        val spinnerGuru   = dialogView.findViewById<Spinner>(R.id.spinnerGuru)
        val etMessage     = dialogView.findViewById<EditText>(R.id.etMessage)

        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            guruNames
        )
        spinnerAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        spinnerGuru.adapter = spinnerAdapter

        AlertDialog.Builder(requireContext())
            .setTitle("Post Thank You Note")
            .setView(dialogView)
            .setPositiveButton("Post") { _, _ ->

                val studentName = etStudentName.text.toString().trim()
                val guruName    = spinnerGuru.selectedItem.toString()
                val message     = etMessage.text.toString().trim()

                // Validate fields
                if (studentName.isEmpty() || message.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Please fill all fields",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setPositiveButton
                }

                val today = SimpleDateFormat(
                    "yyyy-MM-dd", Locale.getDefault()
                ).format(Date())

                val newNote = Appreciation(studentName, guruName, message, today)

                // ✅ Save to Firebase
                FirebaseRepository.saveAppreciation(newNote) { success ->
                    if (success) {
                        Toast.makeText(
                            requireContext(),
                            "Saved to Firebase!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Saved locally only",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                // ✅ Update local list immediately
                appreciationList.add(0, newNote)
                adapter.updateList(appreciationList)

                Toast.makeText(
                    requireContext(),
                    "Thank You note posted!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}