package com.nimmaguru.app.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.nimmaguru.app.GuruProfileActivity
import com.nimmaguru.app.LocaleHelper
import com.nimmaguru.app.MainActivity
import com.nimmaguru.app.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val btnRegisterGuru  = view.findViewById<Button>(R.id.btnRegisterGuru)
        val btnLanguage      = view.findViewById<Button>(R.id.btnLanguage)

        // Set button label based on current language
        val currentLang = LocaleHelper.getSavedLanguage(requireContext())
        btnLanguage.text = if (currentLang == "en") "Switch to ಕನ್ನಡ" else "Switch to English"

        // Open Guru Profile
        btnRegisterGuru.setOnClickListener {
            startActivity(Intent(requireContext(), GuruProfileActivity::class.java))
        }

        // Switch Language
        btnLanguage.setOnClickListener {
            val newLang = if (currentLang == "en") "kn" else "en"
            LocaleHelper.setLocale(requireContext(), newLang)
            // Restart MainActivity to apply language
            (requireActivity() as MainActivity).restartActivity()
        }

        return view
    }
}