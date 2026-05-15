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

        val btnRegisterGuru = view.findViewById<Button>(R.id.btnRegisterGuru)
        val btnLanguage     = view.findViewById<Button>(R.id.btnLanguage)

        val currentLang = LocaleHelper.getSavedLanguage(requireContext())
        updateLanguageButton(btnLanguage, currentLang)

        btnRegisterGuru.setOnClickListener {
            startActivity(Intent(requireContext(), GuruProfileActivity::class.java))
        }

        btnLanguage.setOnClickListener {
            val newLang = if (currentLang == "en") "kn" else "en"

            // Apply and save language
            LocaleHelper.setLocale(requireContext(), newLang)

            // Full app restart
            val intent = Intent(requireActivity(), MainActivity::class.java)
            intent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                        Intent.FLAG_ACTIVITY_NEW_TASK
            )
            startActivity(intent)
            requireActivity().finish()
        }

        return view
    }

    private fun updateLanguageButton(btn: Button, lang: String) {
        btn.text = if (lang == "en") "Switch to ಕನ್ನಡ" else "Switch to English"
    }
}