package com.nimmaguru.app

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import java.util.Locale

object LocaleHelper {

    private const val PREF_NAME  = "NimmaGuruPrefs"
    private const val KEY_LANG   = "selected_language"

    fun setLocale(context: Context, lang: String) {
        persist(context, lang)
        applyLocale(context, lang)
    }

    fun getSavedLanguage(context: Context): String {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_LANG, "en") ?: "en"
    }

    fun applyLocale(context: Context, lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    private fun persist(context: Context, lang: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_LANG, lang).apply()
    }
}