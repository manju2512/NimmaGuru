package com.nimmaguru.app

import android.content.Context
import android.content.SharedPreferences

object LanguageManager {

    private const val PREF_NAME = "NimmaGuruPrefs"
    private const val KEY_LANG  = "selected_language"

    fun saveLanguage(context: Context, lang: String) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit().putString(KEY_LANG, lang).apply()
    }

    fun getLanguage(context: Context): String {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(KEY_LANG, "en") ?: "en"
    }

    fun isKannada(context: Context) = getLanguage(context) == "kn"

    // ── Home ──────────────────────────────────────────
    fun homeWelcome(ctx: Context)   = if (isKannada(ctx)) "ನಿಮ್ಮ ಗುರುಗೆ ಸ್ವಾಗತ"           else "Welcome to NimmaGuru"
    fun homeSubtitle(ctx: Context)  = if (isKannada(ctx)) "ಗುರುಗಳನ್ನು ವಿದ್ಯಾರ್ಥಿಗಳೊಂದಿಗೆ ಸಂಪರ್ಕಿಸುತ್ತಿದೆ" else "Connecting Gurus with Students"
    fun homeRegisterBtn(ctx: Context) = if (isKannada(ctx)) "ಗುರುವಾಗಿ ನೋಂದಾಯಿಸಿ"         else "Register as Guru"
    fun homeLangBtn(ctx: Context)   = if (isKannada(ctx)) "Switch to English"              else "Switch to ಕನ್ನಡ"

    // ── Search ────────────────────────────────────────
    fun searchTitle(ctx: Context)   = if (isKannada(ctx)) "ಗುರುವನ್ನು ಹುಡುಕಿ"             else "Find a Guru"
    fun searchHint(ctx: Context)    = if (isKannada(ctx)) "ಹೆಸರು ಅಥವಾ ಗ್ರಾಮದಿಂದ ಹುಡುಕಿ..." else "Search by name or village..."

    // ── Calendar ──────────────────────────────────────
    fun calendarTitle(ctx: Context)      = if (isKannada(ctx)) "ತರಗತಿ ಕ್ಯಾಲೆಂಡರ್"          else "Class Calendar"
    fun calendarNoSessions(ctx: Context) = if (isKannada(ctx)) "ಈ ದಿನಾಂಕದಂದು ಅಧಿವೇಶನಗಳಿಲ್ಲ" else "No sessions scheduled for this date"
    fun calendarSessionsOn(ctx: Context) = if (isKannada(ctx)) "ಅಧಿವೇಶನಗಳು: "              else "Sessions on: "

    // ── Appreciation ──────────────────────────────────
    fun appreciateTitle(context: Context): String {
        return if (isKannada(context)) { "ಪ್ರಶಂಸೆ ಫಲಕ"                } else {
            "Appreciation Wall"
        }
    }
    fun appreciateSubtitle(context: Context): String {
        return if (isKannada(context)) { "ವಿದ್ಯಾರ್ಥಿಗಳಿಂದ ಧನ್ಯವಾದ ಟಿಪ್ಪಣಿಗಳು"  } else {
            "Post thank you notes for teachers"
        }
    }

    // ── Nav ───────────────────────────────────────────
    fun navHome(ctx: Context)       = if (isKannada(ctx)) "ಮನೆ"        else "Home"
    fun navSearch(ctx: Context)     = if (isKannada(ctx)) "ಹುಡುಕಿ"     else "Search"
    fun navCalendar(ctx: Context)   = if (isKannada(ctx)) "ಕ್ಯಾಲೆಂಡರ್" else "Calendar"
    fun navAppreciate(ctx: Context) = if (isKannada(ctx)) "ಧನ್ಯವಾದ"    else "Appreciate"
}